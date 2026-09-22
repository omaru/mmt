package com.haynespro.assessment.mmt.api.application.usecases;

import com.haynespro.assessment.mmt.api.application.services.IdentificationService;
import com.haynespro.assessment.mmt.api.domain.Make;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetMakeByIdUseCase {

  private final IdentificationService identificationService;

  public Make execute(Command command) {
    return identificationService.getMakeById(command.makeId);
  }

  public record Command(int makeId) {}
}
