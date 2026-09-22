package com.haynespro.assessment.mmt.api.infrastructure.adapters;

import static org.assertj.core.api.Assertions.assertThat;

import com.haynespro.assessment.mmt.api.application.ports.TypeRepositoryPort;
import com.haynespro.assessment.mmt.api.domain.Type;
import com.haynespro.assessment.mmt.util.Data;
import java.util.List;
import org.hibernate.Hibernate;
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

  @Test
  void shouldListTypesWithoutLoadingTheirLazyModel() {
    List<Type> types = sut.findAllByModelId(Data.MUSTANG.getId());

    assertThat(types).allSatisfy(type -> assertThat(type.getModel()).isNull());
    assertThat(typeRepository.findAllByModelEntityId(Data.MUSTANG.getId()))
        .allSatisfy(
            entity -> assertThat(Hibernate.isInitialized(entity.getModelEntity())).isFalse());
  }

  @Test
  void shouldFindTypeByIdWithItsModelAndMake() {
    Type type = sut.findById(Data.MUSTANG_38L_2003.getId());

    assertThat(type.getName()).isEqualTo(Data.MUSTANG_38L_2003.getName());
    assertThat(type.getModel().getId()).isEqualTo(Data.MUSTANG.getId());
    assertThat(type.getModel().getName()).isEqualTo("Mustang");
    assertThat(type.getModel().getMake().getName()).isEqualTo("Ford");
  }

  @Test
  void shouldReturnNullWhenTypeIdDoesNotExist() {
    assertThat(sut.findById(999999)).isNull();
  }
}
