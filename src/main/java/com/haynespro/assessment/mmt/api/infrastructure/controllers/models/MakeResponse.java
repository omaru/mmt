package com.haynespro.assessment.mmt.api.infrastructure.controllers.models;

import com.haynespro.assessment.mmt.api.domain.Make;
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
public class MakeResponse {
  private final Integer id;
  private final String name;

  public static MakeResponse from(Make make) {
    return new MakeResponse(make.getId(), make.getName());
  }
}
