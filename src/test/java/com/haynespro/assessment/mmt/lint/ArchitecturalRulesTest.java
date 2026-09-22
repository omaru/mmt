package com.haynespro.assessment.mmt.lint;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

class ArchitecturalRulesTest {

  private static final String APP_ROOT = "com.haynespro.assessment.mmt";
  private static final String ROOT_PACKAGE = APP_ROOT + ".api";
  private static final String DOMAIN = ROOT_PACKAGE + ".domain";
  private static final String APPLICATION = ROOT_PACKAGE + ".application";
  private static final String INFRASTRUCTURE = ROOT_PACKAGE + ".infrastructure";

  private static JavaClasses productionClasses;

  @BeforeAll
  static void importClasses() {
    productionClasses =
        new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages(APP_ROOT);
  }

  @Test
  void allProductionClassesAreWithinAnArchitecturalLayer() {
    classes()
        .that()
        .resideInAPackage(APP_ROOT + "..")
        .and()
        .areNotAnnotatedWith(SpringBootApplication.class)
        .should()
        .resideInAnyPackage(DOMAIN + "..", APPLICATION + "..", INFRASTRUCTURE + "..")
        .check(productionClasses);
  }

  @Test
  void architecturalBoundariesAreRespected() {
    noClasses()
        .that()
        .resideInAPackage(DOMAIN + "..")
        .should()
        .dependOnClassesThat()
        .resideInAnyPackage(APPLICATION + "..", INFRASTRUCTURE + "..")
        .check(productionClasses);

    noClasses()
        .that()
        .resideInAPackage(APPLICATION + "..")
        .should()
        .dependOnClassesThat()
        .resideInAnyPackage(INFRASTRUCTURE + "..")
        .check(productionClasses);
  }

  @Test
  void controllersOnlyTalkToUseCases() {
    noClasses()
        .that()
        .resideInAPackage(INFRASTRUCTURE + ".controllers..")
        .should()
        .dependOnClassesThat()
        .resideInAnyPackage(APPLICATION + ".services..", APPLICATION + ".ports..")
        .check(productionClasses);
  }

  @Test
  void servicesDoNotDependOnUseCases() {
    noClasses()
        .that()
        .resideInAPackage(APPLICATION + ".services..")
        .should()
        .dependOnClassesThat()
        .resideInAPackage(APPLICATION + ".usecases..")
        .check(productionClasses);
  }

  @Test
  void persistenceEntitiesDoNotKnowTheDomain() {
    noClasses()
        .that()
        .resideInAPackage(INFRASTRUCTURE + ".adapters.entities..")
        .should()
        .dependOnClassesThat()
        .resideInAPackage(DOMAIN + "..")
        .check(productionClasses);
  }

  @Test
  void domainLayerShouldNotHaveExternalDependency() {
    noExternalDependency(DOMAIN);
  }

  @Test
  void applicationLayerShouldNotHaveExternalDependency() {
    noExternalDependency(APPLICATION);
  }

  private void noExternalDependency(String layerPackage) {
    classes()
        .that()
        .resideInAPackage(layerPackage + "..")
        .should()
        .onlyDependOnClassesThat()
        .resideInAnyPackage("java..", "javax..", "lombok..", APP_ROOT + "..")
        .check(productionClasses);
  }
}
