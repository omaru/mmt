package com.haynespro.assessment.mmt.benchmark;

import com.haynespro.assessment.mmt.api.infrastructure.WebServiceApplication;
import com.haynespro.assessment.mmt.api.infrastructure.adapters.TypeRepository;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(2)
public class MmtTypeFindAllBenchmark {

  private ConfigurableApplicationContext context;
  private TypeRepository typeRepository;
  private static final Integer FORD_FOCUS_MODEL_ID = 103;

  @Setup(Level.Trial)
  public void setUp() {
    SpringApplication application = new SpringApplication(WebServiceApplication.class);
    application.setAdditionalProfiles("integration");
    context = application.run("--server.port=0");
    typeRepository = context.getBean(TypeRepository.class);
  }

  @TearDown(Level.Trial)
  public void tearDown() {
    if (context != null) {
      context.close();
    }
  }

  @Benchmark
  public void findAllByModel() {
    typeRepository.findAllByModelEntityId(FORD_FOCUS_MODEL_ID);
  }
}
