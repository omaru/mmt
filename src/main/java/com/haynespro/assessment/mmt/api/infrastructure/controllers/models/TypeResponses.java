package com.haynespro.assessment.mmt.api.infrastructure.controllers.models;

import com.haynespro.assessment.mmt.api.domain.ModelTypes;
import java.util.List;
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
public class TypeResponses {
  private final ModelResponse model;
  private final List<TypeResponse> types;

  public static TypeResponses from(ModelTypes modelTypes) {
    return new TypeResponses(
        ModelResponse.from(modelTypes.getModel()),
        modelTypes.getTypes().stream().map(TypeResponse::from).toList());
  }
}
