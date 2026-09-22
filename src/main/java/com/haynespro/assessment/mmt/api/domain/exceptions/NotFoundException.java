package com.haynespro.assessment.mmt.api.domain.exceptions;

public abstract class NotFoundException extends RuntimeException {

  protected NotFoundException(String message) {
    super(message);
  }
}
