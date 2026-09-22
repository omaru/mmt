package com.haynespro.assessment.mmt.api.application.ports;

import com.haynespro.assessment.mmt.api.domain.Type;
import java.util.List;

public interface TypeRepositoryPort {
  List<Type> findAllByModelId(Integer modelId);

  Type findById(Integer id);
}
