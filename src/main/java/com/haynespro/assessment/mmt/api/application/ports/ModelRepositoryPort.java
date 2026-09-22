package com.haynespro.assessment.mmt.api.application.ports;

import com.haynespro.assessment.mmt.api.domain.Model;
import java.util.List;

public interface ModelRepositoryPort {
  List<Model> findAllByMakeId(Integer makeId);

  Model findById(int i);
}
