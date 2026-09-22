# Developing

This document explains how to set up your environment and run the `mmt` (Make/Model/Type) service locally.

## Prerequisites

- **Java 21** (the project is built and run against Java 21, see `java.version` in `pom.xml`)
- **Maven 3.9+** (no Maven Wrapper is committed, so use a locally installed `mvn`)
- No external database is required — the service uses an in-memory H2 database

## Running the service locally

1. Build the project:

   ```bash
   mvn clean install
   ```

2. Run the application:

   ```bash
   mvn spring-boot:run
   ```
   By default the service starts on **port 8081** 

3. Optionally, run with the `local` profile to seed the H2 database with sample make/model/type data on startup (see [Infrastructure](#infrastructure) below):

   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=local
   ```

4. To authenticate, send the credentials configured under `service.user` / `service.password` (default `mmt` / `mmt`) as JSON to `POST /v1/authentication/login`. The response body is `{"token":"<jwt>"}`, and the same token is also sent in the `X-AUTH-TOKEN` header. Requests to the rest of the API must carry that JWT as a Bearer token. HTTP Basic is disabled.

   ```bash
   curl -X POST http://localhost:8081/v1/authentication/login \
     -H 'Content-Type: application/json' \
     -d '{"username":"mmt","password":"mmt"}'

   curl http://localhost:8081/v1/identification/makes -H 'Authorization: Bearer <jwt>'
   ```

5. The API is documented via springdoc-openapi/Swagger UI, available once the app is running at `/swagger-ui.html`. The root path `/` redirects there.

## Running the tests

Tests are split by suffix, and each group has its own Maven profile:

| Suffix | Kind | Spring profile | Command |
|---|---|---|---|
| `*Test` | Unit tests and architecture rules | — | `mvn verify` (always run) |
| `*CT` | Component tests (repository adapters and controllers against H2) | `component` | `mvn verify -Pcomponent` |
| `*IT` | Integration tests (full application with seed data) | `integration` | `mvn verify -Pintegration` |

- **Unit tests:** `*Test` classes always run with surefire.
- **Component tests:** the `component` Maven profile makes surefire also include `*CT`.
- **Integration tests:** the `integration` Maven profile runs `*IT` with failsafe in the `integration-test` phase and fails the build in `verify`.

The Maven profiles (`-P`) only choose which tests run. The Spring profile each test uses is set in the test itself with `@ActiveProfiles` (see `CTBase` and `ITBase`).

Run everything:

```bash
mvn verify -Pcomponent,integration
```

## Code formatting (Spotless)

The code is formatted with [Spotless](https://github.com/diffplug/spotless) using Google Java Format. `spotless:check` is bound to the `verify` phase, so `mvn verify` / `mvn clean install` fail if any file is not properly formatted, with an error like:

```
The following files had format violations: ...
```

To fix it, reformat the code and run the build again:

```bash
mvn spotless:apply
```

You can also check the formatting without changing any file:

```bash
mvn spotless:check
```

## Infrastructure

### Database (H2)

The service uses an **in-memory H2 database** (`jdbc:h2:mem:mmt`), so no external database setup is needed.

`application.yml` sets `spring.h2.console.enabled: true`, but the H2 console is **not** available: `/h2-console` returns 404. In Spring Boot 4 the console lives in the separate `spring-boot-h2console` module, which is not a dependency of this project. Making it work would require adding that dependency and allowing `/h2-console/**` in `SecurityConfiguration`.

Because the database is in-memory, its schema and data are recreated from scratch on every application restart via Flyway migrations.

### Schema migrations (Flyway)

Flyway manages the database schema. The baseline migrations live in `src/main/resources/db/migrations` and are always applied, regardless of the active Spring profile:

- `V1__schema.sql`: creates the `MMT_MAKE`, `MMT_MODEL` and `MMT_TYPE` tables.
- `V2__add_indexes.sql`: adds the indexes used to look up models by make and types by model.

### Seed data

Sample data (Make/Model/Type records) lives in `src/main/resources/db/seed-data/V10000__testdata.sql`. It is treated as an additional Flyway migration location and is **not** applied by default — it's only added to `spring.flyway.locations` under specific profiles:

- `local` (`src/main/resources/application-local.yml`): for running the service locally.
- `integration` (`src/test/resources/application-integration.yml`): used by `AppIT` and the Gatling/JMH benchmarks.
- `component` (`src/test/resources/application-component.yml`): used by the `*CT` tests. It also includes `db/test-fixtures/component`.

The default (production) profile never loads `db/seed-data`, so seed data is never applied outside of local development and tests.

### Authentication / JWT

The service issues and validates JWTs using an RSA key pair configured via:

- `jwt.private.key` → `classpath:keys/jwt.private.pem`
- `jwt.public.key` → `classpath:keys/jwt.public.pem`

These are development keys checked into `src/main/resources/keys/` and are only intended for local use.
