package com.haynespro.assessment.mmt.api.infrastructure.adapters;

import com.haynespro.assessment.mmt.api.infrastructure.adapters.entities.ModelEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends JpaRepository<ModelEntity, Integer> {

  List<ModelEntity> findAllByMakeEntityId(int makeId);
}
