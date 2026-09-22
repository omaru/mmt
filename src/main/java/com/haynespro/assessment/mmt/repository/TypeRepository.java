package com.haynespro.assessment.mmt.repository;

import com.haynespro.assessment.mmt.model.Type;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Integer> {

  List<Type> findAllByModelId(int modelId);
}
