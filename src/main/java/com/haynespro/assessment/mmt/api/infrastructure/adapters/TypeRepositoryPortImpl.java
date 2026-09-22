package com.haynespro.assessment.mmt.api.infrastructure.adapters;

import com.haynespro.assessment.mmt.api.application.ports.TypeRepositoryPort;
import com.haynespro.assessment.mmt.api.domain.Type;
import com.haynespro.assessment.mmt.api.infrastructure.adapters.mappers.EntityMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TypeRepositoryPortImpl implements TypeRepositoryPort {
  private final TypeRepository typeRepository;

  @Override
  public List<Type> findAllByModelId(Integer modelId) {
    return typeRepository.findAllByModelEntityId(modelId).stream()
        .map(EntityMapper::toType)
        .toList();
  }

  @Override
  public Type findById(Integer id) {
    return typeRepository.findWithModelById(id).map(EntityMapper::toTypeWithModel).orElse(null);
  }
}
