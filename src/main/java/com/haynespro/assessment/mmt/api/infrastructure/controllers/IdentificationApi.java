package com.haynespro.assessment.mmt.api.infrastructure.controllers;

import com.haynespro.assessment.mmt.api.application.usecases.GetAllMakesUseCase;
import com.haynespro.assessment.mmt.api.application.usecases.GetMakeByIdUseCase;
import com.haynespro.assessment.mmt.api.application.usecases.GetModelUseCase;
import com.haynespro.assessment.mmt.api.application.usecases.GetModelsByMakeUseCase;
import com.haynespro.assessment.mmt.api.application.usecases.GetTypeUseCase;
import com.haynespro.assessment.mmt.api.application.usecases.GetTypesByModelUseCase;
import com.haynespro.assessment.mmt.api.infrastructure.controllers.models.MakeResponse;
import com.haynespro.assessment.mmt.api.infrastructure.controllers.models.ModelResponse;
import com.haynespro.assessment.mmt.api.infrastructure.controllers.models.TypeResponse;
import com.haynespro.assessment.mmt.api.infrastructure.controllers.models.TypeResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequiredArgsConstructor
public class IdentificationApi {
  private final GetAllMakesUseCase getMakes;
  private final GetMakeByIdUseCase getMake;
  private final GetModelsByMakeUseCase getModelsByMake;
  private final GetModelUseCase getModel;
  private final GetTypesByModelUseCase getTypesByModel;
  private final GetTypeUseCase getType;

  @Operation(summary = "Get all makes")
  @GetMapping("/makes")
  public ResponseEntity<List<MakeResponse>> getMakes() {
    return ResponseEntity.ok(getMakes.execute().stream().map(MakeResponse::from).toList());
  }

  @Operation(summary = "Get a single make")
  @GetMapping("/make/{makeId}")
  @ApiResponse(
      responseCode = "200",
      description = "Retrieve make by id",
      useReturnTypeSchema = true)
  @ApiResponse(responseCode = "404", description = "Make not found")
  public ResponseEntity<MakeResponse> getMake(@PathVariable Integer makeId) {
    return ResponseEntity.ok(
        MakeResponse.from(getMake.execute(new GetMakeByIdUseCase.Command(makeId))));
  }

  @Operation(summary = "Get models for a given make (id)")
  @GetMapping("/models/{makeId}")
  public ResponseEntity<List<ModelResponse>> getModelsByMakeId(@PathVariable Integer makeId) {
    return ResponseEntity.ok(
        getModelsByMake.execute(new GetModelsByMakeUseCase.Command(makeId)).stream()
            .map(ModelResponse::from)
            .toList());
  }

  @Operation(summary = "Get a single model")
  @GetMapping("/model/{modelId}")
  @ApiResponse(
      responseCode = "200",
      description = "Retrieve model by id",
      useReturnTypeSchema = true)
  @ApiResponse(responseCode = "404", description = "Model not found")
  public ResponseEntity<ModelResponse> getModel(@PathVariable Integer modelId) {
    return ResponseEntity.ok(
        ModelResponse.from(getModel.execute(new GetModelUseCase.Command(modelId))));
  }

  @Operation(summary = "Get types for a given model (id)")
  @GetMapping("/types/{modelId}")
  @ApiResponse(
      responseCode = "200",
      description = "Retrieve types by model id",
      useReturnTypeSchema = true)
  @ApiResponse(responseCode = "404", description = "Model not found")
  public ResponseEntity<TypeResponses> getTypesByModelId(@PathVariable Integer modelId) {
    return ResponseEntity.ok(
        TypeResponses.from(getTypesByModel.execute(new GetTypesByModelUseCase.Command(modelId))));
  }

  @Operation(summary = "Get a single type")
  @GetMapping("/type/{typeId}")
  @ApiResponse(
      responseCode = "200",
      description = "Retrieve engine type by id",
      useReturnTypeSchema = true)
  @ApiResponse(responseCode = "404", description = "Type not found")
  public ResponseEntity<TypeResponse> getType(@PathVariable Integer typeId) {
    return ResponseEntity.ok(
        TypeResponse.from(getType.execute(new GetTypeUseCase.Command(typeId))));
  }
}
