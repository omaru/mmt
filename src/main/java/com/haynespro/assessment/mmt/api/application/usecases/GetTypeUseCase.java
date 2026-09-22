package com.haynespro.assessment.mmt.api.application.usecases;

import com.haynespro.assessment.mmt.api.application.services.IdentificationService;
import com.haynespro.assessment.mmt.api.domain.Type;
import com.haynespro.assessment.mmt.api.domain.exceptions.TypeNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetTypeUseCase {

  private final IdentificationService identificationService;

  public Type execute(Command command) {
    Type type = identificationService.getTypeById(command.typeId);
    if (type == null) {
      throw new TypeNotFoundException(command.typeId);
    }
    return type;
  }

  public record Command(Integer typeId) {}
}
