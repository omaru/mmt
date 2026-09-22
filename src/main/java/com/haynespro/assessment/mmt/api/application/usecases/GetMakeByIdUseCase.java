package com.haynespro.assessment.mmt.api.application.usecases;

import com.haynespro.assessment.mmt.api.application.services.IdentificationService;
import com.haynespro.assessment.mmt.api.domain.Make;
import com.haynespro.assessment.mmt.api.domain.exceptions.MakeNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetMakeByIdUseCase {

  private final IdentificationService identificationService;

  public Make execute(Command command) {
    Make make = identificationService.getMakeById(command.makeId);
    if (make == null) {
      throw new MakeNotFoundException(command.makeId);
    }
    return make;
  }

  public record Command(int makeId) {}
}
