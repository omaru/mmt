package com.haynespro.assessment.mmt.repository;

import com.haynespro.assessment.mmt.model.Make;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MakeRepository extends JpaRepository<Make, Integer> {}
