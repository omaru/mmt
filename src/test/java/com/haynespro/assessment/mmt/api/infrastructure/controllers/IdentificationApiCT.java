package com.haynespro.assessment.mmt.api.infrastructure.controllers;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.haynespro.assessment.mmt.api.infrastructure.controllers.models.MakeResponse;
import com.haynespro.assessment.mmt.api.infrastructure.controllers.models.ModelResponse;
import com.haynespro.assessment.mmt.api.infrastructure.controllers.models.TypeResponse;
import com.haynespro.assessment.mmt.api.infrastructure.controllers.models.TypeResponses;
import com.haynespro.assessment.mmt.helper.CTBase;
import com.haynespro.assessment.mmt.util.Data;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class IdentificationApiCT extends CTBase {

  private static final int UNKNOWN_ID = 999999;

  @Test
  void shouldReturnAllMakes() {
    MakeResponse[] response =
        authenticatedRequest
            .when()
            .get("/v1/identification/makes")
            .then()
            .statusCode(200)
            .extract()
            .as(MakeResponse[].class);

    assertThat(response)
        .extracting(MakeResponse::getName)
        .containsExactlyInAnyOrder("Ford", "Lincoln", "Mercury");
  }

  @Test
  void shouldReturnMakeById() {
    MakeResponse response =
        authenticatedRequest
            .when()
            .get("/v1/identification/make/" + Data.FORD.getId())
            .then()
            .statusCode(200)
            .extract()
            .as(MakeResponse.class);

    assertThat(response.getId()).isEqualTo(Data.FORD.getId());
    assertThat(response.getName()).isEqualTo("Ford");
  }

  @Test
  void shouldReturnModelsByMakeId() {
    ModelResponse[] response =
        authenticatedRequest
            .when()
            .get("/v1/identification/models/" + Data.FORD.getId())
            .then()
            .statusCode(200)
            .extract()
            .as(ModelResponse[].class);

    assertThat(response).hasSize(165);
    assertThat(response)
        .allSatisfy(model -> assertThat(model.getMake().getName()).isEqualTo("Ford"));
  }

  @Test
  void shouldReturnAnEmptyListWhenRequestingModelsOfAnUnknownMake() {
    ModelResponse[] response =
        authenticatedRequest
            .when()
            .get("/v1/identification/models/" + UNKNOWN_ID)
            .then()
            .statusCode(200)
            .extract()
            .as(ModelResponse[].class);

    assertThat(response).isEmpty();
  }

  @Test
  void shouldReturnModelById() {
    ModelResponse response =
        authenticatedRequest
            .when()
            .get("/v1/identification/model/" + Data.MUSTANG.getId())
            .then()
            .statusCode(200)
            .extract()
            .as(ModelResponse.class);

    assertThat(response.getId()).isEqualTo(Data.MUSTANG.getId());
    assertThat(response.getName()).isEqualTo("Mustang");
    assertThat(response.getMake().getName()).isEqualTo("Ford");
  }

  @Test
  void shouldReturnTheModelOnceWithItsTypesWhenRequestingTypesByModelId() {
    TypeResponses response =
        authenticatedRequest
            .when()
            .get("/v1/identification/types/" + Data.MUSTANG.getId())
            .then()
            .statusCode(200)
            .extract()
            .as(TypeResponses.class);

    assertThat(response.getModel().getId()).isEqualTo(Data.MUSTANG.getId());
    assertThat(response.getTypes()).hasSize(105);
  }

  @Test
  void shouldReturnTypeById() {
    TypeResponse response =
        authenticatedRequest
            .when()
            .get("/v1/identification/type/" + Data.MUSTANG_38L_2003.getId())
            .then()
            .statusCode(200)
            .extract()
            .as(TypeResponse.class);

    assertThat(response.getId()).isEqualTo(Data.MUSTANG_38L_2003.getId());
    assertThat(response.getName()).isEqualTo(Data.MUSTANG_38L_2003.getName());
    assertThat(response.getYear()).isEqualTo(Data.MUSTANG_38L_2003.getYear());
    assertThat(response.getModel().getId()).isEqualTo(Data.MUSTANG.getId());
    assertThat(response.getModel().getName()).isEqualTo("Mustang");
    assertThat(response.getModel().getMake().getName()).isEqualTo("Ford");
  }

  @ParameterizedTest
  @ValueSource(strings = {"make", "model", "types", "type"})
  void shouldReturn404WhenRequestingAnUnknownId(String resource) {
    authenticatedRequest
        .when()
        .get("/v1/identification/" + resource + "/" + UNKNOWN_ID)
        .then()
        .statusCode(404);
  }

  @Test
  void shouldReturn401WhenNotAuthenticated() {
    given().when().get("/v1/identification/makes").then().statusCode(401);
  }
}
