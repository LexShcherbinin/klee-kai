package com.github.lexshcherbinin.kleekai.helpers;

import static io.qameta.allure.util.ResultsUtils.EPIC_LABEL_NAME;
import static io.qameta.allure.util.ResultsUtils.FEATURE_LABEL_NAME;
import static io.qameta.allure.util.ResultsUtils.PARENT_SUITE_LABEL_NAME;
import static io.qameta.allure.util.ResultsUtils.SUITE_LABEL_NAME;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Label;

/**
 * Класс для редактирования label`ов в allure-отчёте.
 */
public class AllureReport {

  private AllureReport() {

  }

  public static AllureReport getInstance() {
    return new AllureReport();
  }

  /**
   * Задаёт имя теста в allure-отчёте.
   *
   * @param name - имя теста
   */
  public AllureReport setName(String name) {
    Allure.getLifecycle().updateTestCase(testResult -> testResult.setName(name));
    return this;
  }

  /**
   * Очищает все label`ы тестов в allure-отчёте.
   */
  public AllureReport clearLabels() {
    Allure.getLifecycle().updateTestCase(testResult -> testResult.getLabels().clear());
    return this;
  }

  /**
   * Задаёт label`ы тестов в allure-отчёте.
   *
   * @param labelName - имя label`а
   * @param name      - задаваемое значение
   */
  public AllureReport setLabel(String labelName, String name) {
    Allure.getLifecycle().updateTestCase(testResult -> {
      testResult.getLabels().add(
          new Label().setName(labelName).setValue(name)
      );
    });
    return this;
  }

  public AllureReport setEpic(String name) {
    setLabel(EPIC_LABEL_NAME, name);
    return this;
  }

  public AllureReport setFeature(String name) {
    setLabel(FEATURE_LABEL_NAME, name);
    return this;
  }

  public AllureReport setParentSuite(String name) {
    setLabel(PARENT_SUITE_LABEL_NAME, name);
    return this;
  }

  public AllureReport setSuite(String name) {
    setLabel(SUITE_LABEL_NAME, name);
    return this;
  }

}
