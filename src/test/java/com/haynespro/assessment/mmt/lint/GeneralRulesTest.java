package com.haynespro.assessment.mmt.lint;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noMethods;
import static org.assertj.core.api.Assertions.assertThat;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchCondition;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class GeneralRulesTest {

  private static final String APP_ROOT = "com.haynespro";
  private static final List<Path> SOURCE_ROOTS =
      List.of(Path.of("src", "main", "java"), Path.of("src", "test", "java"));
  private static final Pattern PACKAGE_DECLARATION =
      Pattern.compile("^\\s*package\\s+([\\w.]+)\\s*;", Pattern.MULTILINE);
  private static final Pattern WILDCARD_IMPORT =
      Pattern.compile("^\\s*import\\s+(static\\s+)?[\\w.]+\\.\\*\\s*;", Pattern.MULTILINE);

  private static JavaClasses projectClasses;

  @BeforeAll
  static void importClasses() {
    projectClasses = new ClassFileImporter().importPackages(APP_ROOT);
  }

  @Test
  void noEmptyFilesAllowed() {
    List<String> emptyFiles =
        sourceFiles().filter(file -> read(file).isBlank()).map(Path::toString).toList();

    assertThat(emptyFiles).as("Empty source files").isEmpty();
  }

  @Test
  void packageNameMustMatchFilePath() {
    List<String> mismatches =
        SOURCE_ROOTS.stream()
            .filter(Files::isDirectory)
            .flatMap(
                root ->
                    sourceFiles(root)
                        .filter(file -> !declaredPackage(file).equals(expectedPackage(root, file)))
                        .map(
                            file ->
                                file
                                    + " declares '"
                                    + declaredPackage(file)
                                    + "' but expected '"
                                    + expectedPackage(root, file)
                                    + "'"))
            .toList();

    assertThat(mismatches).as("Package/path mismatches").isEmpty();
  }

  @Test
  void noWildcardImportsAllowed() {
    List<String> wildcardImports =
        sourceFiles()
            .flatMap(
                file ->
                    WILDCARD_IMPORT
                        .matcher(read(file))
                        .results()
                        .map(match -> file + ": " + match.group().trim()))
            .toList();

    assertThat(wildcardImports).as("Wildcard imports").isEmpty();
  }

  // experimental: if we encounter too many false positives, we can remove this rule
  @Test
  void noNullableCollectionsInReturnTypes() {
    noMethods()
        .that(returnACollection())
        .should(ArchCondition.from(haveANullableReturnType()))
        .because("collection return types should be empty instead of null")
        .allowEmptyShould(true)
        .check(projectClasses);
  }

  private static DescribedPredicate<JavaMethod> returnACollection() {
    return DescribedPredicate.describe(
        "return a collection or map",
        method ->
            method.getRawReturnType().isAssignableTo(Collection.class)
                || method.getRawReturnType().isAssignableTo(Map.class));
  }

  private static DescribedPredicate<JavaMethod> haveANullableReturnType() {
    return DescribedPredicate.describe(
        "have a @Nullable return type",
        method ->
            method.getAnnotations().stream()
                    .anyMatch(annotation -> isNullable(annotation.getRawType().getSimpleName()))
                || Arrays.stream(method.reflect().getAnnotatedReturnType().getAnnotations())
                    .anyMatch(
                        annotation -> isNullable(annotation.annotationType().getSimpleName())));
  }

  private static boolean isNullable(String annotationName) {
    return annotationName.equals("Nullable");
  }

  private static Stream<Path> sourceFiles() {
    return SOURCE_ROOTS.stream().filter(Files::isDirectory).flatMap(GeneralRulesTest::sourceFiles);
  }

  private static Stream<Path> sourceFiles(Path root) {
    try {
      return Files.walk(root).filter(file -> file.toString().endsWith(".java")).toList().stream();
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  private static String declaredPackage(Path file) {
    Matcher matcher = PACKAGE_DECLARATION.matcher(read(file));
    return matcher.find() ? matcher.group(1) : "";
  }

  private static String expectedPackage(Path root, Path file) {
    Path directory = root.relativize(file).getParent();
    return directory == null
        ? ""
        : directory.toString().replace(file.getFileSystem().getSeparator(), ".");
  }

  private static String read(Path file) {
    try {
      return Files.readString(file);
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }
}
