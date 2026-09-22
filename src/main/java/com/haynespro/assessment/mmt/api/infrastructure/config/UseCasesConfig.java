package com.haynespro.assessment.mmt.api.infrastructure.config;

import com.haynespro.assessment.mmt.api.application.services.IdentificationService;
import com.haynespro.assessment.mmt.api.application.usecases.GetAllMakesUseCase;
import com.haynespro.assessment.mmt.api.application.usecases.GetMakeByIdUseCase;
import com.haynespro.assessment.mmt.api.application.usecases.GetModelUseCase;
import com.haynespro.assessment.mmt.api.application.usecases.GetModelsByMakeUseCase;
import com.haynespro.assessment.mmt.api.application.usecases.GetTypeUseCase;
import com.haynespro.assessment.mmt.api.application.usecases.GetTypesByModelUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCasesConfig {

  @Bean
  public GetAllMakesUseCase getAllMakesUseCase(IdentificationService identificationService) {
    return new GetAllMakesUseCase(identificationService);
  }

  @Bean
  public GetMakeByIdUseCase getMakeByIdUseCase(IdentificationService identificationService) {
    return new GetMakeByIdUseCase(identificationService);
  }

  @Bean
  public GetModelsByMakeUseCase getModelsByMakeUseCase(
      IdentificationService identificationService) {
    return new GetModelsByMakeUseCase(identificationService);
  }

  @Bean
  public GetModelUseCase getModelUseCase(IdentificationService identificationService) {
    return new GetModelUseCase(identificationService);
  }

  @Bean
  public GetTypesByModelUseCase getTypesByModelUseCase(
      IdentificationService identificationService) {
    return new GetTypesByModelUseCase(identificationService);
  }

  @Bean
  public GetTypeUseCase getTypeUseCase(IdentificationService identificationService) {
    return new GetTypeUseCase(identificationService);
  }
}
