package com.haynespro.assessment.mmt.api.infrastructure.adapters;

import com.haynespro.assessment.mmt.api.application.ports.ModelRepositoryPort;
import com.haynespro.assessment.mmt.api.domain.Model;
import com.haynespro.assessment.mmt.api.infrastructure.adapters.mappers.EntityMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ModelRepositoryPortImpl implements ModelRepositoryPort {
  private final ModelRepository modelRepository;

  @Override
  public List<Model> findAllByMakeId(Integer makeId) {
    return modelRepository.findAllByMakeEntityId(makeId).stream()
        .map(EntityMapper::toModel)
        .toList();
  }

  @Override
  public Model findById(int i) {
    return modelRepository.findById(i).map(EntityMapper::toModel).orElse(null);
  }
}
