package com.haynespro.assessment.mmt.api.infrastructure.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import com.haynespro.assessment.mmt.api.infrastructure.controllers.models.TypeResponses;
import com.haynespro.assessment.mmt.helper.CTBase;
import org.junit.jupiter.api.Test;

class IdentificationApiCT extends CTBase {

  @Test
  void shouldReturnTheModelOnceWithItsTypesWhenRequestingTypesByModelId() {
    TypeResponses response =
        authenticatedRequest
            .when()
            .get("/v1/identification/types/103")
            .then()
            .statusCode(200)
            .extract()
            .as(TypeResponses.class);

    assertThat(response.getModel().getId()).isEqualTo(103);
    assertThat(response.getTypes()).isNotEmpty();
  }

  @Test
  void shouldReturn404WhenRequestingTypesOfAnUnknownModel() {
    authenticatedRequest.when().get("/v1/identification/types/999999").then().statusCode(404);
  }
}
