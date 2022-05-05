package com.github.lexshcherbinin.kleekai.steps;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.github.lexshcherbinin.kleekai.ui.BasePage;
import io.qameta.allure.Step;

/**
 * Шаги для взаимодействия с радиобаттонами страницы
 */
public interface RadioButtonSteps<T extends BasePage<T>> {

  @Step("Выполнен клик по радиобаттону '{elementName}'")
  default T clickRadioButton(String elementName) {
    ((BasePage<?>) this).getElement(elementName).click();
    return (T) this;
  }

  @Step("Проверка, что радиобаттон '{elementName}' отображается на странице")
  default T checkRadioButtonIsDisplayed(String elementName) {
    ((BasePage<?>) this).getElement(elementName).should(Condition.visible);
    return (T) this;
  }

  @Step("Проверка, что радиобаттон '{elementName}' не отображается на странице")
  default T checkRadioButtonIsNotDisplayed(String elementName) {
    ((BasePage<?>) this).getElement(elementName).shouldNot(Condition.visible);
    return (T) this;
  }

  @Step("Проверка, что радиобаттон '{elementName}' кликабелен")
  default T checkRadioButtonIsClickable(String elementName) {
    SelenideElement element = ((BasePage<?>) this).getElement(elementName);

    assertTrue(
        element.is(Condition.enabled),
        String.format("Радиобаттон '%s' не кликабелен", elementName));

    return (T) this;
  }

  @Step("Проверка, что радиобаттон '{elementName}' не кликабелен")
  default T checkRadioButtonIsNotClickable(String elementName) {
    SelenideElement element = ((BasePage<?>) this).getElement(elementName);

    assertFalse(
        element.is(Condition.enabled),
        String.format("Радиобаттон '%s' кликабелен", elementName));

    return (T) this;
  }

  @Step("Проверка, что радиобаттон '{elementName}' выбран")
  default T checkRadioButtonIsSelected(String elementName) {
    ((BasePage<?>) this).getElement(elementName).should(Condition.enabled);
    return (T) this;
  }

  @Step("Проверка, что радиобаттон '{elementName}' не выбран")
  default T checkRadioButtonIsNotSelected(String elementName) {
    ((BasePage<?>) this).getElement(elementName).shouldNot(Condition.enabled);
    return (T) this;
  }

}