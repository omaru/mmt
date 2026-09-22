package com.haynespro.assessment.mmt.helper;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = {"component"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class CTBase {

  @LocalServerPort protected int port;

  protected RequestSpecification authenticatedRequest;

  @BeforeEach
  void setUpRestAssured() {
    RestAssured.port = port;
    RestAssured.baseURI = "http://localhost";
    login();
  }

  private void login() {
    String token =
        given()
            .contentType(ContentType.JSON)
            .body(
                """
                        { "username": "mmt", "password": "mmt" }
                        """)
            .when()
            .post("/v1/authentication/login")
            .then()
            .statusCode(200)
            .extract()
            .path("token");

    this.authenticatedRequest =
        given().header("Authorization", "Bearer " + token).contentType(ContentType.JSON);
  }
}
