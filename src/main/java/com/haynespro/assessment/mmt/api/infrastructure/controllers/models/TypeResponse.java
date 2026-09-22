package com.haynespro.assessment.mmt.api.infrastructure.controllers.models;

import com.haynespro.assessment.mmt.api.domain.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TypeResponse {
  private final Integer id;
  private final String name;
  private final String year;

  public static TypeResponse from(Type type) {
    return new TypeResponse(type.getId(), type.getName(), type.getYear());
  }
}
