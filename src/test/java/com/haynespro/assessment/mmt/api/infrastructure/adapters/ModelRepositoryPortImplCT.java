package com.haynespro.assessment.mmt.api.infrastructure.adapters;

import static org.assertj.core.api.Assertions.assertThat;

import com.haynespro.assessment.mmt.api.application.ports.ModelRepositoryPort;
import com.haynespro.assessment.mmt.api.domain.Model;
import com.haynespro.assessment.mmt.util.Data;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("component")
class ModelRepositoryPortImplCT {

  @Autowired private ModelRepository modelRepository;

  private ModelRepositoryPort sut;

  @BeforeEach
  void setUp() {
    sut = new ModelRepositoryPortImpl(modelRepository);
  }

  @Test
  void shouldFindAllModelsFromTestFixtures() {
    List<Model> models = sut.findAllByMakeId(Data.FORD.getId());

    assertThat(models).hasSize(165);
  }

  @Test
  void shouldFindModelByIdFromTestFixtures() {
    Model model = sut.findById(Data.MUSTANG.getId());

    assertThat(model).isNotNull();
    assertThat(model.getId()).isEqualTo(Data.MUSTANG.getId());
    assertThat(model.getName()).isEqualTo(Data.MUSTANG.getName());
  }
}
