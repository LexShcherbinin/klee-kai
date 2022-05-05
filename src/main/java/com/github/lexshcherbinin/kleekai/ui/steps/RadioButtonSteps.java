package com.github.lexshcherbinin.kleekai.ui.steps;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.github.lexshcherbinin.kleekai.ui.KleeKaiPage;
import io.qameta.allure.Step;

/**
 * Шаги для взаимодействия с радиобаттонами страницы
 */
public interface RadioButtonSteps<T extends KleeKaiPage<T>> {

  @Step("Выполнен клик по радиобаттону '{elementName}'")
  default T clickRadioButton(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).click();
    return (T) this;
  }

  @Step("Проверка, что радиобаттон '{elementName}' отображается на странице")
  default T checkRadioButtonIsDisplayed(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).should(Condition.visible);
    return (T) this;
  }

  @Step("Проверка, что радиобаттон '{elementName}' не отображается на странице")
  default T checkRadioButtonIsNotDisplayed(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).shouldNot(Condition.visible);
    return (T) this;
  }

  @Step("Проверка, что радиобаттон '{elementName}' кликабелен")
  default T checkRadioButtonIsClickable(String elementName) {
    SelenideElement element = ((KleeKaiPage<?>) this).getElement(elementName);

    assertTrue(
        element.is(Condition.enabled),
        String.format("Радиобаттон '%s' не кликабелен", elementName));

    return (T) this;
  }

  @Step("Проверка, что радиобаттон '{elementName}' не кликабелен")
  default T checkRadioButtonIsNotClickable(String elementName) {
    SelenideElement element = ((KleeKaiPage<?>) this).getElement(elementName);

    assertFalse(
        element.is(Condition.enabled),
        String.format("Радиобаттон '%s' кликабелен", elementName));

    return (T) this;
  }

  @Step("Проверка, что радиобаттон '{elementName}' выбран")
  default T checkRadioButtonIsSelected(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).should(Condition.enabled);
    return (T) this;
  }

  @Step("Проверка, что радиобаттон '{elementName}' не выбран")
  default T checkRadioButtonIsNotSelected(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).shouldNot(Condition.enabled);
    return (T) this;
  }

}