package com.haynespro.assessment.mmt.api.infrastructure.controllers;

import com.haynespro.assessment.mmt.api.domain.exceptions.ModelNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(ModelNotFoundException.class)
  public ProblemDetail handleModelNotFound(ModelNotFoundException exception) {
    return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
  }
}
