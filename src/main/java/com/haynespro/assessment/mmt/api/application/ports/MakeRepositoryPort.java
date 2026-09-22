package com.haynespro.assessment.mmt.api.application.ports;

import com.haynespro.assessment.mmt.api.domain.Make;
import java.util.List;

public interface MakeRepositoryPort {
  List<Make> findAll();

  Make findById(Integer id);
}
