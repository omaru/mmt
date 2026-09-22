package com.haynespro.assessment.mmt.api.application.usecases;

import com.haynespro.assessment.mmt.api.application.services.IdentificationService;
import com.haynespro.assessment.mmt.api.domain.Model;
import com.haynespro.assessment.mmt.api.domain.ModelTypes;
import com.haynespro.assessment.mmt.api.domain.exceptions.ModelNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetTypesByModelUseCase {

  private final IdentificationService identificationService;

  public ModelTypes execute(Command command) {
    Model model = identificationService.getModelById(command.modelId);
    if (model == null) {
      throw new ModelNotFoundException(command.modelId);
    }
    return new ModelTypes(model, identificationService.getTypesByModelId(command.modelId));
  }

  public record Command(Integer modelId) {}
}
