package com.haynespro.assessment.mmt.api.infrastructure;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.haynespro.assessment.mmt.api.application.services.IdentificationService;
import com.haynespro.assessment.mmt.api.application.usecases.GetTypesByModelUseCase;
import com.haynespro.assessment.mmt.api.infrastructure.adapters.TypeRepository;
import com.haynespro.assessment.mmt.helper.ITBase;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AppIT extends ITBase {

  private Integer FORD_MAKE_ID = 1;
  private Integer FORD_FOCUS = 103;
  @Autowired TypeRepository typeRepository;

  @Autowired IdentificationService identificationService;

  @Autowired GetTypesByModelUseCase getTypesByModelUseCase;

  @Test
  public void engineTypesShouldMatch() {
    assertThat(typeRepository.findAll().size()).isEqualTo(10379);
  }

  @Test
  @Transactional
  public void shouldRetrieveEngineTypesByGivenMake() {
    var models = identificationService.getModelsByMakeId(FORD_MAKE_ID);
    assertThat(models.size()).isEqualTo(165);
  }

  @Test
  @Transactional
  public void shouldRetrieveEngineTypesByGivenModel() {
    var types = getTypesByModelUseCase.execute(new GetTypesByModelUseCase.Command(FORD_FOCUS));
    assertThat(types.getTypes().size()).isEqualTo(965);
  }
}
