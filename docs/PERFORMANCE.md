# Performance

This document explains how to run the performance/load benchmarks for the `mmt` service.

## What's included

- **Gatling load test** — `IdentificationTypesGatlingSimulation` (`src/test/java/com/haynespro/assessment/mmt/benchmark/gatling`), which exercises `GET /v1/identification/types/{modelId}` for the Ford Focus (`modelId=103`). It runs three scenarios, each with **10 concurrent users**, totalling **50**, **100**, and **1000** requests respectively.
- **JMH micro-benchmark** — `MmtTypeFindAllBenchmark` (`src/test/java/com/haynespro/assessment/mmt/benchmark`), which measures `TypeRepository.findAllByModelEntityId` directly against the database.

Both boot the application in-process against the `integration` profile (in-memory H2 seeded with test data), so no external server or database setup is required.

## Running the benchmarks

All benchmark tooling lives behind the `benchmark` Maven profile.

Run everything (Gatling + JMH) as part of the full verify lifecycle:

```bash
mvn verify -Pbenchmark
```

Run only the Gatling load test:

```bash
mvn gatling:test -Pbenchmark
```

Skip unit tests and the Spotless format check for a faster iteration loop:

```bash
mvn gatling:test -Pbenchmark -DskipTests -Dspotless.check.skip=true
```

## Reading the results

- The Gatling HTML report is written to `target/gatling/<simulation-id>/index.html` and printed at the end of the run — open it in a browser to see request counts, response time percentiles, and throughput per scenario.
- The JMH results are printed to the console at the end of the `verify` run, summarizing average time per operation.
- Reports from previous runs (`no_indexes`, `indexes`, `types_by_model`) are kept in `docs/benchmark/gatling`. See the [Suggestions](README.md#suggestions) section of the README for a comparison.
