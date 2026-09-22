package com.haynespro.assessment.mmt.api.infrastructure.adapters;

import com.haynespro.assessment.mmt.api.infrastructure.adapters.entities.TypeEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<TypeEntity, Integer> {

  List<TypeEntity> findAllByModelEntityId(int modelId);
}
