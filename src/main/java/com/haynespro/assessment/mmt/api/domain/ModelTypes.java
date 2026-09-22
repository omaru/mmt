package com.haynespro.assessment.mmt.api.domain;

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
public class ModelTypes {
  private final Model model;
  private final List<Type> types;
}
