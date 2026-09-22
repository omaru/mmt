package com.haynespro.assessment.mmt.application.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.haynespro.assessment.mmt.api.application.ports.MakeRepositoryPort;
import com.haynespro.assessment.mmt.api.application.ports.ModelRepositoryPort;
import com.haynespro.assessment.mmt.api.application.ports.TypeRepositoryPort;
import com.haynespro.assessment.mmt.api.application.services.IdentificationService;
import com.haynespro.assessment.mmt.api.domain.Make;
import com.haynespro.assessment.mmt.api.domain.Model;
import com.haynespro.assessment.mmt.api.domain.Type;
import com.haynespro.assessment.mmt.util.Data;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class IdentificationServiceTest {
  private IdentificationService sut;
  @Mock private MakeRepositoryPort makeRepository;
  @Mock private ModelRepositoryPort modelRepository;
  @Mock private TypeRepositoryPort typeRepository;

  @BeforeEach
  void setUp() {
    sut = new IdentificationService(makeRepository, modelRepository, typeRepository);
  }

  @Test
  void shouldRetrieveAllDomainMakes() {
    when(makeRepository.findAll()).thenReturn(Data.ALL_MAKES);
    List<Make> makes = sut.getAllMakes();
    assertThat(makes).hasSize(3);
  }

  @Test
  void shouldRetrieveDomainMakeById() {
    when(makeRepository.findById(1)).thenReturn(Data.FORD);
    Make make = sut.getMakeById(1);
    assertThat(make).isNotNull();
  }

  @Test
  void shouldRetrieveDomainModelsByMakeId() {
    when(modelRepository.findAllByMakeId(1)).thenReturn(Data.MODELS_BY_FORD);
    List<Model> models = sut.getModelsByMakeId(1);
    assertThat(models).hasSize(1);
  }

  @Test
  void shouldRetrieveDomainTypesByModelId() {
    when(typeRepository.findAllByModelId(123)).thenReturn(Data.TYPES_BY_MUSTANG);
    List<Type> types = sut.getTypesByModelId(123);
    assertThat(types).isEqualTo(Data.TYPES_BY_MUSTANG);
  }
}
