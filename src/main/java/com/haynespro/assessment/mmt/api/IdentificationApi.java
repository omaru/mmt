package com.haynespro.assessment.mmt.api;

import com.haynespro.assessment.mmt.model.Make;
import com.haynespro.assessment.mmt.model.Model;
import com.haynespro.assessment.mmt.model.Type;
import com.haynespro.assessment.mmt.service.IdentificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/identification")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(
    name = "The Identifcation API",
    description =
        """
				The Identifcation API provides the methods to retrieve
				the type identification from the database.
				""")
public class IdentificationApi {

  private IdentificationService identificationService;

  public IdentificationApi(IdentificationService identificationService) {
    super();
    this.identificationService = identificationService;
  }

  @Operation(summary = "Get all makes")
  @GetMapping("/makes")
  public List<Make> getMakes() {
    return identificationService.getAllMakes();
  }

  @Operation(summary = "Get a single make")
  @GetMapping("/make/{makeId}")
  public Make getMake(int makeId) {
    return identificationService.getMakeById(makeId);
  }

  @Operation(summary = "Get models for a given make (id)")
  @GetMapping("/models/{makeId}")
  public List<Model> getModlesByMakeId(int makeId) {
    return identificationService.getModelsByMakeId(makeId);
  }

  @Operation(summary = "Get a single model")
  @GetMapping("/model/{modelId}")
  public Model getModel(int modelId) {
    return identificationService.getModelById(modelId);
  }

  @Operation(summary = "Get types for a given model (id)")
  @GetMapping("/types/{modelId}")
  public List<Type> getTypesByModelId(int modelId) {
    return identificationService.getTypesByModelId(modelId);
  }

  @Operation(summary = "Get a single type")
  @GetMapping("/type/{typeId}")
  public Type getType(int typeId) {
    return identificationService.getTypeById(typeId);
  }
}
