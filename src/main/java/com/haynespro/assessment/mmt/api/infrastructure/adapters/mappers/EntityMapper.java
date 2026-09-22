package com.haynespro.assessment.mmt.api.infrastructure.adapters.mappers;

import com.haynespro.assessment.mmt.api.domain.Make;
import com.haynespro.assessment.mmt.api.domain.Model;
import com.haynespro.assessment.mmt.api.domain.Type;
import com.haynespro.assessment.mmt.api.infrastructure.adapters.entities.MakeEntity;
import com.haynespro.assessment.mmt.api.infrastructure.adapters.entities.ModelEntity;
import com.haynespro.assessment.mmt.api.infrastructure.adapters.entities.TypeEntity;

public final class EntityMapper {

  private EntityMapper() {}

  public static Make toMake(MakeEntity entity) {
    return Make.builder().id(entity.getId()).name(entity.getName()).build();
  }

  public static Model toModel(ModelEntity entity) {
    return Model.builder()
        .id(entity.getId())
        .make(toMake(entity.getMakeEntity()))
        .category(entity.getCategory())
        .name(entity.getName())
        .build();
  }

  public static Type toType(TypeEntity entity) {
    return Type.builder().id(entity.getId()).name(entity.getName()).year(entity.getYear()).build();
  }

  public static Type toTypeWithModel(TypeEntity entity) {
    return Type.builder()
        .id(entity.getId())
        .model(toModel(entity.getModelEntity()))
        .name(entity.getName())
        .year(entity.getYear())
        .build();
  }
}
