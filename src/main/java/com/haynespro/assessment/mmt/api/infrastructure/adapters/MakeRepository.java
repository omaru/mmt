package com.haynespro.assessment.mmt.api.infrastructure.adapters;

import com.haynespro.assessment.mmt.api.infrastructure.adapters.entities.MakeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MakeRepository extends JpaRepository<MakeEntity, Integer> {}
