package com.haynespro.assessment.mmt.api.domain;

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
public class Model {
  private final Integer id;
  private final Make make;
  private final String category;
  private final String name;
}
