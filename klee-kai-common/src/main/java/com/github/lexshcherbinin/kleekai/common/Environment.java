package com.github.lexshcherbinin.kleekai.common;

import static java.util.stream.Collectors.toList;

import com.codeborne.selenide.logevents.SelenideLogger;
import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;
import io.qameta.allure.Allure;
import io.qameta.allure.model.StepResult;
import io.qameta.allure.selenide.AllureSelenide;
import java.util.List;
import java.util.Properties;

/**
 * Класс с методами по настройке окружения.
 */
public final class Environment {

  private Environment() {

  }

  /**
   * Задание настроек окружения из файла selenide.properties.
   */
  public static void setupSelenideEnvironment() {
    String selenidePropertiesPath = "src/main/resources/selenide.properties";
    Properties properties = PropertyLoader.loadPropertiesFile(selenidePropertiesPath);

    for (Object property : properties.keySet()) {
      String key = property.toString();
      String value = System.getProperty(key);

      if (value == null) {
        value = properties.getProperty(key);
      }

      System.setProperty(key, value);
    }
  }

  /**
   * Подключение AllureSelenideLogger.
   */
  public static void setupAllureReports() {
    SelenideLogger.addListener(
        "AllureSelenideLogger",
        new AllureSelenide().screenshots(true).savePageSource(true).includeSelenideSteps(false)
    );
  }

  /**
   * Поиск в application.conf директории, в которой будут создаваться тестовые файлы. Возвращает значение
   * temp_test_data_path из application.conf. При его отсутствии возвращает значение
   * target/generated-sources/test-data/.
   *
   * @return - возвращает путь, по которому будут создаваться тестовые файлы
   */
  public static String getTestDataPath() {
    String defaultPath = "target/generated-sources/test-data/";
    String tempTestDataPath;

    try {
      tempTestDataPath = ConfigFactory.load().getString("temp_test_data_path");

    } catch (ConfigException e) {
      tempTestDataPath = defaultPath;
    }

    if (tempTestDataPath.equals("")) {
      return defaultPath;
    }

    return tempTestDataPath;
  }

  /**
   * Скрытие параметров в шагах отчёта.
   */
  public static void hideParametersInSteps() {
    Allure.getLifecycle()
        .updateTestCase(
            update -> {
              List<StepResult> processed = processStepResult(update.getSteps());
              update.setSteps(processed);
            });
  }

  /**
   * Реверсивное скрытие параметров в шагах отчёта в самих шагах.
   *
   * @param stepResults - шаги в отчёте.
   * @return - возвращает "зачищенные" шаги.
   */
  private static List<StepResult> processStepResult(List<StepResult> stepResults) {
    if (stepResults.size() != 0) {
      return stepResults.stream()
          .peek(
              stepResult -> {
                stepResult.setParameters(List.of());
                stepResult.setSteps(processStepResult(stepResult.getSteps()));
              })
          .collect(toList());
    }

    return List.of();
  }

}
