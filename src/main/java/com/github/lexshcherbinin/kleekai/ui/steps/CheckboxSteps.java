package com.github.lexshcherbinin.kleekai.ui.steps;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.github.lexshcherbinin.kleekai.ui.KleeKaiPage;
import io.qameta.allure.Step;

/**
 * Шаги для взаимодействия с чекбоксами страницы
 */
public interface CheckboxSteps<T extends KleeKaiPage<T>> {

  @Step("Выполнен клик по чекбоксу '{elementName}'")
  default T clickCheckbox(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).click();
    return (T) this;
  }

  @Step("Проверка, что чекбокс '{elementName}' отображается на странице")
  default T checkCheckboxIsDisplayed(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).should(Condition.visible);
    return (T) this;
  }

  @Step("Проверка, что чекбокс '{elementName}' не отображается на странице")
  default T checkCheckboxIsNotDisplayed(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).shouldNot(Condition.visible);
    return (T) this;
  }

  @Step("Проверка, что чекбокс '{elementName}' кликабелен")
  default T checkCheckboxIsClickable(String elementName) {
    SelenideElement element = ((KleeKaiPage<?>) this).getElement(elementName);

    assertTrue(
        element.is(Condition.enabled),
        String.format("Чекбокс '%s' не кликабелен", elementName));

    return (T) this;
  }

  @Step("Проверка, что чекбокс '{elementName}' не кликабелен")
  default T checkCheckboxIsNotClickable(String elementName) {
    SelenideElement element = ((KleeKaiPage<?>) this).getElement(elementName);

    assertFalse(
        element.is(Condition.enabled),
        String.format("Чекбокс '%s' кликабелен", elementName));

    return (T) this;
  }

  @Step("Проверка, что чекбокс '{elementName}' выбран")
  default T checkCheckboxIsSelected(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).should(Condition.enabled);
    return (T) this;
  }

  @Step("Проверка, что чекбокс '{elementName}' не выбран")
  default T checkCheckboxIsNotSelected(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).shouldNot(Condition.enabled);
    return (T) this;
  }

}