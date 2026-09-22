package com.haynespro.assessment.mmt.api.infrastructure.config;

import com.haynespro.assessment.mmt.api.application.ports.MakeRepositoryPort;
import com.haynespro.assessment.mmt.api.application.ports.ModelRepositoryPort;
import com.haynespro.assessment.mmt.api.application.ports.TypeRepositoryPort;
import com.haynespro.assessment.mmt.api.application.services.IdentificationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfig {
  @Bean
  public IdentificationService identificationService(
      MakeRepositoryPort makeRepository,
      ModelRepositoryPort modelRepository,
      TypeRepositoryPort typeRepository) {
    return new IdentificationService(makeRepository, modelRepository, typeRepository);
  }
}
