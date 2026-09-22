package com.haynespro.assessment.mmt.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.haynespro.assessment.mmt.api.application.services.IdentificationService;
import com.haynespro.assessment.mmt.api.application.usecases.GetTypesByModelUseCase;
import com.haynespro.assessment.mmt.api.domain.ModelTypes;
import com.haynespro.assessment.mmt.api.domain.exceptions.ModelNotFoundException;
import com.haynespro.assessment.mmt.util.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetTypesByModelUseCaseTest {
  private GetTypesByModelUseCase sut;
  @Mock private IdentificationService identificationService;

  @BeforeEach
  void setUp() {
    sut = new GetTypesByModelUseCase(identificationService);
  }

  @Test
  void shouldReturnTheModelWithItsTypes() {
    when(identificationService.getModelById(123)).thenReturn(Data.MUSTANG);
    when(identificationService.getTypesByModelId(123)).thenReturn(Data.TYPES_BY_MUSTANG);

    ModelTypes modelTypes = sut.execute(new GetTypesByModelUseCase.Command(123));

    assertThat(modelTypes.getModel()).isEqualTo(Data.MUSTANG);
    assertThat(modelTypes.getTypes()).isEqualTo(Data.TYPES_BY_MUSTANG);
  }

  @Test
  void shouldThrowAndNotLoadTypesWhenModelDoesNotExist() {
    when(identificationService.getModelById(999)).thenReturn(null);

    assertThatThrownBy(() -> sut.execute(new GetTypesByModelUseCase.Command(999)))
        .isInstanceOf(ModelNotFoundException.class);
    verify(identificationService, never()).getTypesByModelId(anyInt());
  }
}
