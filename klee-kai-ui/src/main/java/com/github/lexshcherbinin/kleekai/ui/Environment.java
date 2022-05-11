package com.github.lexshcherbinin.kleekai.ui;

import static java.util.stream.Collectors.toList;

import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.lexshcherbinin.kleekai.common.PropertyLoader;
import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;
import io.qameta.allure.Allure;
import io.qameta.allure.model.StepResult;
import io.qameta.allure.selenide.AllureSelenide;
import java.util.List;
import java.util.Properties;

/**
 * Класс с методами по настройке окружения
 */
public class Environment {

  /**
   * Задать настройки окружения из файла selenide.properties
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
   * Подключить AllureSelenideLogger
   */
  public static void setupAllureReports() {
    SelenideLogger.addListener(
        "AllureSelenideLogger",
        new AllureSelenide().screenshots(true).savePageSource(true).includeSelenideSteps(false)
    );
  }

  /**
   * Ищет в application.conf директорию, в которой будут создаваться тестовые файлы
   *
   * @return - возвращает значение temp_test_data_path из application.conf. При его отсутствии возвращает значение
   * target/generated-sources/test-data/
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
   * Скрыть параметры в шагах в отчёте
   */
  public static void hideParametersInSteps() {
    Allure.getLifecycle()
        .updateTestCase(
            update -> {
              List<StepResult> processed = processStepResult(update.getSteps());
              update.setSteps(processed);
            });
  }

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
