package com.haynespro.assessment.mmt;

import static org.assertj.core.api.Assertions.assertThat;

import com.haynespro.assessment.mmt.helpers.ITBase;
import com.haynespro.assessment.mmt.repository.TypeRepository;
import com.haynespro.assessment.mmt.service.IdentificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AppIT extends ITBase {

  private Integer FORD_MAKE_ID = 1;
  private Integer F_SUPER_DUTY_MODEL_ID = 1;
  @Autowired TypeRepository typeRepository;

  @Autowired IdentificationService identificationService;

  @Test
  public void engineTypesShouldMatch() {
    assertThat(typeRepository.findAll().size()).isEqualTo(10379);
  }

  @Test
  public void shouldRetrieveEngineTypesByGivenMake() {
    var models = identificationService.getModelsByMakeId(FORD_MAKE_ID);
    assertThat(models.size()).isEqualTo(165);
  }

  @Test
  public void shouldRetrieveEngineTypesByGivenModel() {
    var types = identificationService.getTypesByModelId(F_SUPER_DUTY_MODEL_ID);
    assertThat(types.size()).isEqualTo(40);
  }
}
