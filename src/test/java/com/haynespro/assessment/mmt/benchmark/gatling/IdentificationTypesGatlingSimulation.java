package com.haynespro.assessment.mmt.benchmark.gatling;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.atOnceUsers;
import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.core.CoreDsl.jsonPath;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

import com.haynespro.assessment.mmt.api.infrastructure.WebServiceApplication;
import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class IdentificationTypesGatlingSimulation extends Simulation {

  private static final Integer FORD_FOCUS_MODEL_ID = 103;
  private static final int CONCURRENT_USERS = 10;
  private static final String SERVICE_USER = "mmt";
  private static final String SERVICE_PASSWORD = "mmt";

  private static final ConfigurableApplicationContext context = startApplication();
  private static final int PORT =
      Integer.parseInt(context.getEnvironment().getProperty("local.server.port"));

  private static ConfigurableApplicationContext startApplication() {
    System.setProperty("spring.devtools.restart.enabled", "false");
    SpringApplication application = new SpringApplication(WebServiceApplication.class);
    application.setAdditionalProfiles("integration");
    ConfigurableApplicationContext applicationContext = application.run("--server.port=0");
    Runtime.getRuntime().addShutdownHook(new Thread(applicationContext::close));
    return applicationContext;
  }

  private final HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:" + PORT);

  private final ChainBuilder login =
      exec(
          http("Login")
              .post("/v1/authentication/login")
              .body(
                  StringBody(
                      "{\"username\":\""
                          + SERVICE_USER
                          + "\",\"password\":\""
                          + SERVICE_PASSWORD
                          + "\"}"))
              .asJson()
              .check(jsonPath("$.token").saveAs("authToken")));

  private final ChainBuilder getTypesByModel =
      exec(
          http("Get types for model " + FORD_FOCUS_MODEL_ID)
              .get("/v1/identification/types/" + FORD_FOCUS_MODEL_ID)
              .header("Authorization", "Bearer #{authToken}")
              .check(status().is(200)));

  private ScenarioBuilder scenarioWithRequests(String name, int totalRequests) {
    int repeatsPerUser = totalRequests / CONCURRENT_USERS;
    return scenario(name).exec(login).repeat(repeatsPerUser).on(getTypesByModel);
  }

  {
    setUp(
            scenarioWithRequests("50 requests - 10 concurrent users", 50)
                .injectOpen(atOnceUsers(CONCURRENT_USERS)),
            scenarioWithRequests("100 requests - 10 concurrent users", 100)
                .injectOpen(atOnceUsers(CONCURRENT_USERS)),
            scenarioWithRequests("1000 requests - 10 concurrent users", 1000)
                .injectOpen(atOnceUsers(CONCURRENT_USERS)))
        .protocols(httpProtocol);
  }
}
