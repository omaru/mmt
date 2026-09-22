package com.haynespro.assessment.mmt.api.application.usecases;

import com.haynespro.assessment.mmt.api.application.services.IdentificationService;
import com.haynespro.assessment.mmt.api.domain.Model;
import com.haynespro.assessment.mmt.api.domain.exceptions.ModelNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetModelUseCase {

  private final IdentificationService identificationService;

  public Model execute(Command command) {
    Model model = identificationService.getModelById(command.modelId);
    if (model == null) {
      throw new ModelNotFoundException(command.modelId);
    }
    return model;
  }

  public record Command(Integer modelId) {}
}
