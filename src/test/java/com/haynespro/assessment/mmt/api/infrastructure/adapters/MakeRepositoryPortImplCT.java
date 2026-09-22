package com.haynespro.assessment.mmt.api.infrastructure.adapters;

import static org.assertj.core.api.Assertions.assertThat;

import com.haynespro.assessment.mmt.api.application.ports.MakeRepositoryPort;
import com.haynespro.assessment.mmt.api.domain.Make;
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
class MakeRepositoryPortImplCT {

  @Autowired private MakeRepository makeRepository;

  private MakeRepositoryPort sut;

  @BeforeEach
  void setUp() {
    sut = new MakeRepositoryPortImpl(makeRepository);
  }

  @Test
  void shouldFindAllMakesFromTestFixtures() {
    List<Make> makes = sut.findAll();

    assertThat(makes)
        .hasSize(3)
        .extracting(Make::getName)
        .containsExactlyInAnyOrder("Ford", "Lincoln", "Mercury");
  }

  @Test
  void shouldFindMakeByIdFromTestFixtures() {
    Make make = sut.findById(1);

    assertThat(make).isNotNull();
    assertThat(make.getId()).isEqualTo(1);
    assertThat(make.getName()).isEqualTo("Ford");
  }

  @Test
  void shouldReturnNullWhenMakeIdDoesNotExist() {
    Make make = sut.findById(999);

    assertThat(make).isNull();
  }
}
