package com.github.lexshcherbinin.kleekai.ui.steps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.github.lexshcherbinin.kleekai.ui.BaseMethods;
import com.github.lexshcherbinin.kleekai.ui.KleeKaiPage;
import io.qameta.allure.Step;

/**
 * Шаги для взаимодействия с кнопками страницы
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
    SelenideElement element = ((KleeKaiPage<?>) this).getElement(elementName);

    assertTrue(
        element.is(Condition.enabled),
        String.format("Кнопка '%s' не кликабельна", elementName));

    return (T) this;
  }

  @Step("Проверка, что кнопка '{elementName}' не кликабельна")
  default T checkButtonIsNotClickable(String elementName) {
    SelenideElement element = ((KleeKaiPage<?>) this).getElement(elementName);

    assertFalse(
        element.is(Condition.enabled),
        String.format("Кнопка '%s' кликабельна", elementName));

    return (T) this;
  }

  @Step("Проверка, что кнопка '{elementName}' содержит атрибут '{attribute}' со значением '{expectedValue}'")
  default T checkButtonContainsAtrWithValue(String elementName, String attribute, String expectedValue) {
    SelenideElement element = ((KleeKaiPage<?>) this).getElement(elementName).should(Condition.visible);
    String actualValue = element.attr(attribute);

    assertEquals(
        expectedValue, actualValue,
        String.format("Кнопка '%s' не содержит атрибут '%s' со значением '%s'", elementName, attribute, expectedValue));

    return (T) this;
  }

  @Step("Проверка, что кнопка '{elementName}' имеет цвет '{color}'")
  default T checkButtonColor(String elementName, String color) { //формат цвета = rgba(244, 67, 54, 1)
    SelenideElement element = ((KleeKaiPage<?>) this).getElement(elementName);
    String elementColor = element.getCssValue("color");

    assertTrue(
        elementColor.contains(color),
        BaseMethods.getDefaultErrorMessage(color, elementColor));
//        String.format("Ожидалось, что кнопка %s будет иметь цвет %s, но фактически имеет цвет %s", elementName, color, elementColor));

    return (T) this;
  }

}