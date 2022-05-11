package com.github.lexshcherbinin.kleekai.ui.steps;

import com.codeborne.selenide.Condition;
import com.github.lexshcherbinin.kleekai.ui.KleeKaiPage;
import io.qameta.allure.Step;

/**
 * Шаги для взаимодействия с кнопками страницы.
 */
public interface ButtonSteps<T extends KleeKaiPage<T>> {

  @Step("Выполнен клик по кнопке '{elementName}'")
  default T clickButton(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).click();
    return (T) this;
  }

  @Step("Проверка, что кнопка '{elementName}' отображается на странице")
  default T checkButtonIsDisplayed(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).should(Condition.visible);
    return (T) this;
  }

  @Step("Проверка, что кнопка '{elementName}' не отображается на странице")
  default T checkButtonIsNotDisplayed(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).shouldNot(Condition.visible);
    return (T) this;
  }

  @Step("Проверка, что кнопка '{elementName}' кликабельна")
  default T checkButtonIsClickable(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).should(Condition.enabled);
    return (T) this;
  }

  @Step("Проверка, что кнопка '{elementName}' не кликабельна")
  default T checkButtonIsNotClickable(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).shouldNot(Condition.enabled);
    return (T) this;
  }

  @Step("Проверка, что кнопка '{elementName}' содержит атрибут '{attribute}' со значением '{expectedValue}'")
  default T checkButtonContainsAtrWithValue(String elementName, String attribute, String expectedValue) {
    ((KleeKaiPage<?>) this).getElement(elementName).should(Condition.attribute(attribute, expectedValue));
    return (T) this;
  }

  @Step("Проверка, что кнопка '{elementName}' имеет цвет '{color}'")
  default T checkButtonColor(String elementName, String color) { //формат цвета = rgba(244, 67, 54, 1)
    ((KleeKaiPage<?>) this).getElement(elementName).should(Condition.cssValue("color", color));
    return (T) this;
  }

}