#Make Model Type Service

This Identification Service is a REST service that returns make, model and type information from our database. The code
has been provided and as you can see all requests directly cause a database query to be executed.

Index
- [Design Trade-offs](#design-trade-offs) to see why the architecture was chosen and what the simpler alternative is
- [Out of Scope](#out-of-scope) to see what was intentionally left out
- [Suggestions](#suggestions) to see what could be improved by this service
- [Architecture](ARCHITECTURE.md) to see the hexagonal architecture and the coding rules enforced by tests
- [Developing](DEVELOPING.md) to see how to build, run and test the service locally
- [Performance](PERFORMANCE.md) to see how to run the Gatling and JMH benchmarks
- [Problem Description](PROBLEM.md) to see the problem statement and requirements for this assessment


## Design Trade-offs

The service follows a hexagonal architecture to demonstrate the approach and how its boundaries can be enforced with
tests (see [Architecture](ARCHITECTURE.md)). For an application this simple, a classic three-layer architecture
(controller → service → repository) would be enough. Switching to it would mostly mean adjusting the rules in
`ArchitecturalRulesTest` to that layering.

## Out of Scope

Building a Docker image and deploying it to an EC2 instance was left out on purpose. The focus of this work is on
improving the benchmarks of the service itself. Deploying to EC2 and adding caching and load balancing would be a
separate layer of testing and performance work.

## Suggestions

### Database indexes

The tables had no indexes to query types by model or models by make, so they were added as a Flyway migration in
[`V2__add_indexes.sql`](../src/main/resources/db/migrations/V2__add_indexes.sql).

### Types by model response

When the API returned all the types of a model, each type also carried its `Model`, although the API already knows
which model was requested. The response is now modelled as a single `Model` plus its list of types. `TypeEntity` keeps
the model as a lazy association for the other queries, and `ModelEntity` also loads its make lazily.

### Benchmark results

The Gatling reports for `GET /v1/identification/types/{modelId}` can be browsed in `docs/benchmark/gatling`:

- [`no_indexes`](benchmark/gatling/no_indexes/index.html): results before the indexes were added.
- [`indexes`](benchmark/gatling/indexes/index.html): results with the indexes applied.
- [`types_by_model`](benchmark/gatling/types_by_model/index.html): results with the indexes plus the new response,
  which references the `Model` only once next to the list of types.

| Report | p95 for `/types/{modelId}` |
|---|---|
| `no_indexes` | 105 ms |
| `indexes` | 103 ms |
| `types_by_model` | 97 ms |

The new response is what brought the p95 from **103 ms** down to **97 ms**.

A JMH benchmark was also added for the repository call `TypeRepository.findAllByModelEntityId`, with these results:

```
-- No Indexes
Benchmark                               Mode  Cnt  Score   Error  Units
MmtTypeFindAllBenchmark.findAllByModel  avgt   10  0,531 ± 0,048  ms/op

-- Indexes
Benchmark                               Mode  Cnt  Score   Error  Units
MmtTypeFindAllBenchmark.findAllByModel  avgt   10  0,469 ± 0,108  ms/op
```