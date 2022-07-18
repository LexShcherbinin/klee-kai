package com.github.lexshcherbinin.kleekai.ui.steps;

import static com.github.lexshcherbinin.kleekai.ui.KleeKaiPage.DEFAULT_TIMEOUT;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.github.lexshcherbinin.kleekai.common.ValueStorage;
import com.github.lexshcherbinin.kleekai.ui.BaseMethods;
import com.github.lexshcherbinin.kleekai.ui.KleeKaiPage;
import io.qameta.allure.Step;
import java.time.Duration;
import org.assertj.core.api.Assertions;

/**
 * Шаги для взаимодействия с элементами страницы (кнопки, вкладки, чекбоксы, радиобаттоны и т.д.).
 */
public interface ElementSteps<T extends KleeKaiPage<T>> {

  @Step("Выполнен клик по элементу '{elementName}'")
  default T clickOnElement(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).click();
    return (T) this;
  }

  @Step("Выполнен клик по элементу с текстом '{text}'")
  default T clickOnElementWithText(String text) {
    Selenide.$x(String.format("//*[contains(text(), '%s')]", text)).click();
    return (T) this;
  }

  @Deprecated
  @Step("Выполнен клик по элементу '{elementName}', если он отображается на странице")
  default T clickIfPresent(String elementName) {
    SelenideElement element = ((KleeKaiPage<?>) this).getElement(elementName);

    if (element.isDisplayed()) {
      element.click();
    }

    return (T) this;
  }

  @Step("Выполнен двойной клик по элементу '{elementName}'")
  default T doubleClickOnElement(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).doubleClick();
    return (T) this;
  }

  @Step("Выполнено наведение на элемент '{elementName}'")
  default T elementHover(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).hover();
    return (T) this;
  }

  @Step("Проверка, что элемент '{elementName}' отображается на странице")
  default T checkElementIsDisplayed(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).should(Condition.visible);
    return (T) this;
  }

  @Step("Проверка, что элемент '{elementName}' не отображается на странице")
  default T checkElementIsNotDisplayed(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).should(Condition.disappear);
    return (T) this;
  }

  @Step("Проверка, что элемент '{elementName}' существует на странице")
  default T checkElementIsExist(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).should(Condition.exist);
    return (T) this;
  }

  @Step("Проверка, что элемент '{elementName}' не существует на странице")
  default T checkElementIsNotExist(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).shouldNot(Condition.exist);
    return (T) this;
  }

  @Step("Проверка, что элемент '{elementName}' кликабелен")
  default T checkElementIsClickable(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).should(Condition.enabled);
    return (T) this;
  }

  @Step("Проверка, что элемент '{elementName}' не кликабелен")
  default T checkElementIsNotClickable(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).shouldNot(Condition.enabled);
    return (T) this;
  }

  @Step("Проверка, что элемент '{elementName}' содержит атрибут '{attribute}' со значением '{expectedValue}'")
  default T checkElementContainsAtrWithValue(String elementName, String attribute, String expectedValue) {
    ((KleeKaiPage<?>) this).getElement(elementName).should(Condition.attribute(attribute, expectedValue));
    return (T) this;
  }

  @Step("Проверка, что элемент '{elementName}' содержит текст '{expectedValue}'")
  default T checkElementContainsText(String elementName, String expectedValue) {
    SelenideElement element = ((KleeKaiPage<?>) this).getElement(elementName).should(Condition.visible);
    String actualValue = BaseMethods.getAnyElementText(element);

    Assertions
        .assertThat(actualValue)
        .withFailMessage(BaseMethods.getDefaultErrorMessage(expectedValue, actualValue))
        .contains(expectedValue);

    return (T) this;
  }

  @Step("Проверка, что элемент '{elementName}' содержит текст, равный '{expectedValue}'")
  default T checkElementEqualsText(String elementName, String expectedValue) {
    SelenideElement element = ((KleeKaiPage<?>) this).getElement(elementName).should(Condition.visible);
    String actualValue = BaseMethods.getAnyElementText(element);

    Assertions
        .assertThat(actualValue)
        .withFailMessage(BaseMethods.getDefaultErrorMessage(expectedValue, actualValue))
        .isEqualTo(expectedValue);

    return (T) this;
  }

  @Step("Ожидание появления на странице элемента '{elementName}' в течение '{seconds}' секунд")
  default T waitUntilElementIsDisplayed(String elementName, int seconds) {
    ((KleeKaiPage<?>) this).getElement(elementName).should(Condition.visible, Duration.ofSeconds(seconds));
    return (T) this;
  }

  @Step("Ожидание появления на странице элемента '{elementName}'")
  default T waitUntilElementIsDisplayed(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).should(Condition.visible, DEFAULT_TIMEOUT);
    return (T) this;
  }

  @Step("Ожидание исчезновения на странице элемента '{elementName}'")
  default T waitUntilElementIsDisappear(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).should(Condition.disappear, DEFAULT_TIMEOUT);
    return (T) this;
  }

  @Step("Значение элемента '{elementName}' сохранено в переменную '{key}'")
  default T saveElementValue(String elementName, String key) {
    SelenideElement element = ((KleeKaiPage<?>) this).getElement(elementName);
    String value = BaseMethods.getAnyElementText(element);
    ValueStorage.saveValue(key, value);

    return (T) this;
  }

  @Step("Проверка, что элемент '{elementName}' имеет цвет '{color}'")
  default T checkElementColor(String elementName, String color) { //формат цвета = rgba(244, 67, 54, 1)
    ((KleeKaiPage<?>) this).getElement(elementName).should(Condition.cssValue("color", color));
    return (T) this;
  }

  @Step("Проверка, что элемент '{elementName}' выбран'")
  default T checkElementIsSelected(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).should(Condition.selected);
    return (T) this;
  }

  @Step("Проверка, что элемент '{elementName}' не выбран'")
  default T checkElementIsNotSelected(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).shouldNot(Condition.selected);
    return (T) this;
  }

  @Step("Проверка, что список элементов '{elementName}' пустой")
  default T checkElementListIsEmpty(String elementName) {
    ((KleeKaiPage<?>) this).getElementList(elementName).should(CollectionCondition.size(0));
    return (T) this;
  }

  @Step("Проверка, что список элементов не '{elementName}' пустой")
  default T checkElementListIsNotEmpty(String elementName) {
    ((KleeKaiPage<?>) this).getElementList(elementName).should(CollectionCondition.sizeGreaterThan(0));
    return (T) this;
  }

}