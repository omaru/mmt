package com.haynespro.assessment.mmt.api.application.services;

import com.haynespro.assessment.mmt.api.application.ports.MakeRepositoryPort;
import com.haynespro.assessment.mmt.api.application.ports.ModelRepositoryPort;
import com.haynespro.assessment.mmt.api.application.ports.TypeRepositoryPort;
import com.haynespro.assessment.mmt.api.domain.Make;
import com.haynespro.assessment.mmt.api.domain.Model;
import com.haynespro.assessment.mmt.api.domain.Type;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IdentificationService {

  private final MakeRepositoryPort makeRepository;
  private final ModelRepositoryPort modelRepository;
  private final TypeRepositoryPort typeRepository;

  public List<Make> getAllMakes() {
    return makeRepository.findAll();
  }

  public Make getMakeById(int makeId) {
    return makeRepository.findById(makeId);
  }

  public List<Model> getModelsByMakeId(int makeId) {
    return modelRepository.findAllByMakeId(makeId);
  }

  public Model getModelById(int modelId) {
    return modelRepository.findById(modelId);
  }

  public List<Type> getTypesByModelId(int modelId) {
    return typeRepository.findAllByModelId(modelId);
  }

  public Type getTypeById(int typeId) {
    return typeRepository.findById(typeId);
  }
}
