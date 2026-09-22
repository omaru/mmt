package com.haynespro.assessment.mmt.api.application.usecases;

import com.haynespro.assessment.mmt.api.application.services.IdentificationService;
import com.haynespro.assessment.mmt.api.domain.Model;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetModelUseCase {

  private final IdentificationService identificationService;

  public Model execute(Command command) {
    return identificationService.getModelById(command.modelId);
  }

  public record Command(Integer modelId) {}
}
