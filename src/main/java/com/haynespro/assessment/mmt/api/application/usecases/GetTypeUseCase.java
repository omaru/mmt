package com.haynespro.assessment.mmt.api.application.usecases;

import com.haynespro.assessment.mmt.api.application.services.IdentificationService;
import com.haynespro.assessment.mmt.api.domain.Type;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetTypeUseCase {

  private final IdentificationService identificationService;

  public Type execute(Command command) {
    return identificationService.getTypeById(command.typeId);
  }

  public record Command(Integer typeId) {}
}
