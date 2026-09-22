package com.haynespro.assessment.mmt.api.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class Make {
  private final Integer id;
  private final String name;
}
