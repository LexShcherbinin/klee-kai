package com.github.lexshcherbinin.kleekai.ui;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

import com.codeborne.selenide.SelenideElement;
import com.typesafe.config.ConfigFactory;
import io.qameta.allure.Allure;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    return String.format("Ожидаемое значение - \"%s\", фактическое - \"%s\"", expectedValue, actualValue);
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

}
