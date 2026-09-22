package com.haynespro.assessment.mmt.api.infrastructure.adapters;

import com.haynespro.assessment.mmt.api.infrastructure.adapters.entities.TypeEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<TypeEntity, Integer> {

  List<TypeEntity> findAllByModelEntityId(int modelId);

  // fetches the lazy model and its make in the same query
  @EntityGraph(attributePaths = {"modelEntity", "modelEntity.makeEntity"})
  Optional<TypeEntity> findWithModelById(Integer id);
}
