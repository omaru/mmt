package com.haynespro.assessment.mmt.api.infrastructure.controllers.models;

import com.haynespro.assessment.mmt.api.domain.Model;
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
public class ModelResponse {
  private final Integer id;
  private final MakeResponse make;
  private final String category;
  private final String name;

  public static ModelResponse from(Model model) {
    return new ModelResponse(
        model.getId(), MakeResponse.from(model.getMake()), model.getCategory(), model.getName());
  }
}
