package com.haynespro.assessment.mmt.repository;

import com.haynespro.assessment.mmt.model.Model;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends JpaRepository<Model, Integer> {

  List<Model> findAllByMakeId(int makeId);
}
