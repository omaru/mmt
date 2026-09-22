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
public class Type {
  private final Integer id;

  /**
   * Only set when a single type is loaded. It is {@code null} for the types inside {@link
   * ModelTypes}, which already hold their model once.
   */
  private final Model model;

  private final String name;
  private final String year;
}
