package com.haynespro.assessment.mmt.api.domain.exceptions;

public class ModelNotFoundException extends NotFoundException {

  public ModelNotFoundException(int modelId) {
    super("Model " + modelId + " not found");
  }
}
