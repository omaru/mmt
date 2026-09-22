package com.haynespro.assessment.mmt.api.infrastructure.adapters;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.haynespro.assessment.mmt.api.application.ports.TypeRepositoryPort;
import com.haynespro.assessment.mmt.api.domain.Type;
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
class TypeRepositoryPortImplCT {

  @Autowired private TypeRepository typeRepository;

  private TypeRepositoryPort sut;

  @BeforeEach
  void setUp() {
    sut = new TypeRepositoryPortImpl(typeRepository);
  }

  @Test
  void shouldFindAllTypesFromTestFixtures() {
    List<Type> types = sut.findAllByModelId(Data.MUSTANG.getId());

    assertThat(types).hasSize(105);
  }
}
