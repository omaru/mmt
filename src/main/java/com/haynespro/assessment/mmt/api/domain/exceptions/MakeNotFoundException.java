package com.haynespro.assessment.mmt.api.domain.exceptions;

public class MakeNotFoundException extends NotFoundException {

  public MakeNotFoundException(int makeId) {
    super("Make " + makeId + " not found");
  }
}
