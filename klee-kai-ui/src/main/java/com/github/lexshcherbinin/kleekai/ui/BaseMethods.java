package com.github.lexshcherbinin.kleekai.ui;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static java.util.stream.Collectors.toList;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.lexshcherbinin.kleekai.common.PropertyLoader;
import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;
import io.qameta.allure.Allure;
import io.qameta.allure.model.StepResult;
import io.qameta.allure.selenide.AllureSelenide;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

/**
 * Класс с общеиспользуемыми методами
 */
public class BaseMethods {

  /**
   * Получение значения из application.conf файла.
   *
   * @param value        - путь к искомому значению
   * @param defaultValue - значение, возвращаемое по умолчанию
   * @return - возвращает значение value при его наличии, defaultValue - при отсутствии
   */
  public static Object getConfigValueOrDefault(String value, Object defaultValue) {
    try {
      return ConfigFactory.load().getObject(value);

    } catch (Exception e) {
      return defaultValue;
    }
  }

  /**
   * Получение дефолтного сообщения об ошибке при сравнении двух объектов
   */
  public static String getDefaultErrorMessage(Object expectedValue, Object actualValue) {
    return String.format("Ожидаемое значение - '%s', фактическое - '%s'", expectedValue, actualValue);
  }

  /**
   * Приведение объекта к типу SelenideElement
   */
  public static SelenideElement castToSelenideElement(Object object) {
    if (object instanceof SelenideElement) {
      return (SelenideElement) object;
    }
    return null;
  }

  /**
   * Получение текста элемента, как редактируемого поля, так и статичного элемента по значению элемента
   */
  public static String getAnyElementText(SelenideElement element) {
    if (element.getTagName().equals("input") || element.getTagName().equals("textarea")) {
      return element.getValue();

    } else {
      return element.getText();
    }
  }

  /**
   * Прикрепить excel-файл к отчёту
   */
  public static void attachExelFile(String name, String path) {
    try {
      Allure.getLifecycle().addAttachment(name, "", "xlsx", Files.readAllBytes(Paths.get(path)));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Прикрепить скриншот к отчёту
   */
  public static void takeScreenshot() {
    final byte[] screenshot = ((TakesScreenshot) getWebDriver()).getScreenshotAs(OutputType.BYTES);
    Allure.getLifecycle().addAttachment("Screenshot", "image/png", "png", screenshot);
  }

  /**
   * Очистить все куки
   */
  public static void deleteAllCookies() {
    getWebDriver().manage().deleteAllCookies();
  }

  /**
   * Закрыть браузер
   */
  public static void closeWebDriver() {
    WebDriverRunner.closeWebDriver();
  }

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
   * Создать директорию
   * @param path
   */
  public static void createDirectory(String path) {
    try {
      Files.createDirectory(Paths.get(path));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Удалить директорию
   * @param path
   */
  public static void deleteDirectory(String path) {
    try {
      System.gc();
      FileUtils.deleteDirectory(new File(path));

    } catch (IOException e) {
      e.printStackTrace();
    }
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

  /**
   * Ищет в application.conf дирректорию, в которой будут создаваться тестовые файлы
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
