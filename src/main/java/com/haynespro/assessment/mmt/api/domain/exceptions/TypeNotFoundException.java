package com.haynespro.assessment.mmt.api.domain.exceptions;

public class TypeNotFoundException extends NotFoundException {

  public TypeNotFoundException(int typeId) {
    super("Type " + typeId + " not found");
  }
}
