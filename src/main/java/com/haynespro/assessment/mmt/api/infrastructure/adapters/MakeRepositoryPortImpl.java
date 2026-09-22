package com.haynespro.assessment.mmt.api.infrastructure.adapters;

import com.haynespro.assessment.mmt.api.application.ports.MakeRepositoryPort;
import com.haynespro.assessment.mmt.api.domain.Make;
import com.haynespro.assessment.mmt.api.infrastructure.adapters.mappers.EntityMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MakeRepositoryPortImpl implements MakeRepositoryPort {
  private final MakeRepository makeRepository;

  @Override
  public List<Make> findAll() {
    return makeRepository.findAll().stream().map(EntityMapper::toMake).toList();
  }

  @Override
  public Make findById(Integer id) {
    return makeRepository.findById(id).map(EntityMapper::toMake).orElse(null);
  }
}
