package com.haynespro.assessment.mmt.api.application.usecases;

import com.haynespro.assessment.mmt.api.application.services.IdentificationService;
import com.haynespro.assessment.mmt.api.domain.Model;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetModelsByMakeUseCase {

  private final IdentificationService identificationService;

  public List<Model> execute(Command command) {
    return identificationService.getModelsByMakeId(command.makeId);
  }

  public record Command(int makeId) {}
}
