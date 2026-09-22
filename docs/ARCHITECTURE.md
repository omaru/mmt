# Architecture

This document describes how the `mmt` service is structured and the rules that keep it that way. All rules are
enforced by tests under `src/test/java/com/haynespro/assessment/mmt/lint`, so a violation breaks the build instead of
relying on code review.

## Hexagonal architecture

The service follows a hexagonal (ports & adapters) architecture. All production code lives under
`com.haynespro.assessment.mmt.api` and is split into three layers:

```
com.haynespro.assessment.mmt.api
├── domain             Business model: Make, Model, Type, ModelTypes
│   └── exceptions     Domain errors: NotFoundException and its Make/Model/TypeNotFoundException subclasses
├── application
│   ├── ports          Interfaces the application needs from the outside (MakeRepositoryPort, TypeRepositoryPort, ...)
│   ├── services       IdentificationService: thin access to the ports
│   └── usecases       One class per operation (GetTypesByModelUseCase, ...), each with an execute(...) method
│                      that takes the use case's own Command record (except GetAllMakesUseCase, which has no input)
└── infrastructure
    ├── adapters       Port implementations (*RepositoryPortImpl) and Spring Data repositories
    │   ├── entities   JPA entities, unaware of the domain
    │   └── mappers    EntityMapper: entity → domain
    ├── controllers    REST entry points (IdentificationApi, AuthenticationApi) and ApiExceptionHandler
    │   └── models     API views (TypeResponses, TypeResponse, ModelResponse, MakeResponse)
    ├── config         Spring wiring (ServicesConfig for services, UseCasesConfig for use cases), security and web configuration
    └── WebServiceApplication
```

Dependencies only point inwards:

```
infrastructure ──▶ application ──▶ domain
```

A request flows like this:

```
IdentificationApi ──▶ Get…UseCase.execute(Command) ──▶ IdentificationService ──▶ *RepositoryPort ◀── *RepositoryPortImpl
```

- **Domain** is the core: plain Java objects with no knowledge of Spring, JPA or HTTP.
- **Application** defines the *ports* (interfaces) it needs to load makes, models and types, without knowing how
  they are implemented. Services give thin access to those ports. Use cases orchestrate services and are the only
  entry point for controllers. For example, `GetTypesByModelUseCase` loads the model, throws `ModelNotFoundException`
  if it does not exist, and returns it with its types as `ModelTypes`.
- **Infrastructure** plugs the outside world in:
  - Controllers call use cases and map the domain result to a view:
    `ResponseEntity.ok(TypeResponses.from(getTypesByModel.execute(new GetTypesByModelUseCase.Command(modelId))))`.
  - `ApiExceptionHandler` turns domain exceptions into HTTP errors: any `NotFoundException` → 404.
  - Adapters implement the ports on top of Spring Data JPA and use `EntityMapper` to turn entities into domain
    objects.
  - Spring beans are wired in `config` (`ServicesConfig`, `UseCasesConfig`), so the application layer stays
    framework-free.

### When `Type` carries its `Model`

`TypeEntity.modelEntity` is a lazy `@ManyToOne`. Reading it while mapping would load the model (and its make) as soon
as a type is converted. There are two mapping paths:

- **Listing types (`/types/{modelId}`):** `EntityMapper.toType` never touches `modelEntity`, so the association stays
  lazy and listing is a single query. These `Type`s have `model = null`. The use case loads the model once and returns
  it with the types as `ModelTypes`. `TypeResponses` exposes the model once plus a list of `TypeSummaryResponse`
  (`id`, `name`, `year`).
- **A single type (`/type/{typeId}`):** `TypeRepository.findWithModelById` uses an `@EntityGraph` to fetch the type,
  its model and the make in one joined query. `EntityMapper.toTypeWithModel` then fills `Type.model`, and
  `TypeResponse` includes it as a `ModelResponse`.

## Running the rules

The rules live in `ArchitecturalRulesTest` (layering) and `GeneralRulesTest` (codebase hygiene). They run as part of
the regular `mvn test` / `mvn verify` build.
