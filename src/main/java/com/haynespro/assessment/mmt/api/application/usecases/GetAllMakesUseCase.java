package com.haynespro.assessment.mmt.api.application.usecases;

import com.haynespro.assessment.mmt.api.application.services.IdentificationService;
import com.haynespro.assessment.mmt.api.domain.Make;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAllMakesUseCase {

  private final IdentificationService identificationService;

  public List<Make> execute() {
    return identificationService.getAllMakes();
  }
}
