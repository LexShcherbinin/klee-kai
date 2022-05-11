package com.github.lexshcherbinin.kleekai.ui.steps;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static com.github.lexshcherbinin.kleekai.ui.KleeKaiPage.DEFAULT_TIMEOUT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.github.lexshcherbinin.kleekai.common.ValueKeeper;
import com.github.lexshcherbinin.kleekai.ui.BaseMethods;
import com.github.lexshcherbinin.kleekai.ui.KleeKaiPage;
import io.qameta.allure.Step;
import java.util.List;

/**
 * Шаги для взаимодействия с элементами страницы (кнопки, вкладки, чекбоксы, радиобаттоны и т.д.)
 */
//TODO: Подумать над возможностью/необходимостью добавить .should(Condition.visible) во все методы
public interface ElementSteps<T extends KleeKaiPage<T>> {

  @Step("Выполнен клик по элементу '{elementName}'")
  default T clickOnElement(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).click();
    return (T) this;
  }

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
    SelenideElement element = ((KleeKaiPage<?>) this).getElement(elementName);
    element.should(Condition.visible);

    return (T) this;
  }

  @Step("Проверка, что элемент '{elementName}' не отображается на странице")
  default T checkElementIsNotDisplayed(String elementName) {
    SelenideElement element = ((KleeKaiPage<?>) this).getElement(elementName);
    element.should(Condition.disappear);
//    assertTrue(
//        element.is(Condition.disappear),
//        String.format("Элемент '%s' отображается на странице", elementName));

    return (T) this;
  }

  @Step("Проверка, что элемент '{elementName}' кликабелен")
  default T checkElementIsClickable(String elementName) {
    SelenideElement element = ((KleeKaiPage<?>) this).getElement(elementName);

    assertTrue(
        element.is(Condition.enabled),
        String.format("Элемент '%s' не кликабелен", elementName));

    return (T) this;
  }

  @Step("Проверка, что элемент '{elementName}' не кликабелен")
  default T checkElementIsNotClickable(String elementName) {
    SelenideElement element = ((KleeKaiPage<?>) this).getElement(elementName);

    assertFalse(
        element.is(Condition.enabled),
        String.format("Элемент '%s' кликабелен", elementName));

    return (T) this;
  }

//  @Step("Проверка, что все элементы из списка '{elementListName}' кликабельны")
//  default T checkElementListIsClickable(String elementListName) {
//    List<SelenideElement> elementList = ((BasePage<?>) this).getElementList(elementListName);
//    SoftAssert softAssert = new SoftAssert();
//
//    for (SelenideElement element : elementList) {
//      softAssert.assertTrue(
//          element.is(Condition.enabled),
//          String.format("Элемент '%s' не кликабелен", elementListName));
//    }
//
//    softAssert.assertAll();
//    return (T) this;
//  }
//
//  @Step("Проверка, что все элементы из списка '{elementListName}' не кликабельны")
//  default T checkElementListIsNotClickable(String elementListName) {
//    List<SelenideElement> elementList = ((BasePage<?>) this).getElementList(elementListName);
//    SoftAssert softAssert = new SoftAssert();
//
//    for (SelenideElement element : elementList) {
//      softAssert.assertFalse(
//          element.is(Condition.enabled),
//          String.format("Элемент '%s' кликабелен", elementListName));
//    }
//
//    softAssert.assertAll();
//    return (T) this;
//  }

  @Step("Проверка, что элемент '{elementName}' содержит атрибут '{attribute}' со значением '{expectedValue}'")
  default T checkElementContainsAtrWithValue(String elementName, String attribute, String expectedValue) {
    SelenideElement element = ((KleeKaiPage<?>) this).getElement(elementName).should(Condition.visible);
    String actualValue = element.attr(attribute);

    assertEquals(
        expectedValue, actualValue,
        String.format("Элемент '%s' не содержит атрибут '%s' со значением '%s'", elementName, attribute, expectedValue));

    return (T) this;
  }

  @Step("Проверка, что элемент '{elementName}' содержит текст '{text}'")
  default T checkElementContainsText(String elementName, String text) {
    SelenideElement element = ((KleeKaiPage<?>) this).getElement(elementName).should(Condition.visible);
    String actualValue = BaseMethods.getAnyElementText(element);

    assertTrue(
        actualValue.contains(text),
        String.format("Элемент '%s' содержит текст '%s', но не содержит текст '%s'", elementName, actualValue, text));

    return (T) this;
  }

  @Step("Проверка, что элемент '{elementName}' содержит текст '{text}'")
  default T checkElementEqualsText(String elementName, String text) {
    SelenideElement element = ((KleeKaiPage<?>) this).getElement(elementName).should(Condition.visible);
    String actualValue = BaseMethods.getAnyElementText(element);

    assertEquals(
        text, actualValue,
        String.format("Элемент '%s' равен '%s', а не '%s'", elementName, actualValue, text));

    return (T) this;
  }

  @Step("Клик по элементу '{elementName}', который содержит текст '{text}'")
  default T clickElementWithText(String elementName, String text) {
    SelenideElement element = ((KleeKaiPage<?>) this).getElement(elementName).should(Condition.visible);
    String actualValue = BaseMethods.getAnyElementText(element);

    assertTrue(
        actualValue.contains(text),
        String.format("Элемент '%s' содержит текст '%s', но не содержит текст '%s'", elementName, actualValue, text));

    return (T) this;
  }

  @Step("Ожидание появления на странице элемента '{elementName}'")
  default T waitUntilElementIsDisplayed(String elementName) {
    SelenideElement element = ((KleeKaiPage<?>) this).getElement(elementName);
    element.should(Condition.visible);

    return (T) this;
  }

  @Step("Ожидание исчезновения на странице элемента '{elementName}'")
  default T waitUntilElementIsDisappear(String elementName) {
    SelenideElement element = ((KleeKaiPage<?>) this).getElement(elementName);
    element.should(Condition.disappear, DEFAULT_TIMEOUT);

    return (T) this;
  }

  @Step("Значение элемента '{elementName}' сохранено в переменную '{key}'")
  default T saveElementValue(String elementName, String key) {
    SelenideElement element = ((KleeKaiPage<?>) this).getElement(elementName);
    String value = BaseMethods.getAnyElementText(element);
    ValueKeeper.saveValue(key, value);

    return (T) this;
  }

  @Step("Проверка, что элемент '{elementName}' имеет цвет '{color}'")
  default T checkElementColor(String elementName, String color) { //формат цвета = rgba(244, 67, 54, 1)
    SelenideElement element = ((KleeKaiPage<?>) this).getElement(elementName);
    String elementColor = element.getCssValue("color");
//    element.shouldHave(Condition.cssValue("color", color));

    assertTrue(
        elementColor.contains(color),
        BaseMethods.getDefaultErrorMessage(color, elementColor));
//        String.format("Ожидалось, что элемент %s будет иметь цвет %s, но фактически имеет цвет %s", elementName, color, element.getCssValue("color")));

    return (T) this;
  }

  @Step("Проверка, что элемент '{elementName}' не выбран'")
  default T checkElementIsNotSelected(String elementName) {
    SelenideElement element = ((KleeKaiPage<?>) this).getElement(elementName);
    element.shouldNotBe(Condition.selected);

    return (T) this;
  }

  @Step("Проверка, что элемент '{elementName}' выбран'")
  default T checkElementIsSelected(String elementName) {
    SelenideElement element = ((KleeKaiPage<?>) this).getElement(elementName);
    element.shouldBe(Condition.selected);

    return (T) this;
  }

  @Step("Проверка, что список элементов '{elementName}' пустой")
  default T checkElementListIsEmpty(String elementName) {
    List<SelenideElement> elementList = ((KleeKaiPage<?>) this).getElementList(elementName);
    assertEquals(
        elementList.size(), 0,
        String.format("Список элементов '%s' не пустой", elementName));

    return (T) this;
  }

  @Step("Проверка, что список элементов не '{elementName}' пустой")
  default T checkElementListIsNotEmpty(String elementName) {
    List<SelenideElement> elementList = ((KleeKaiPage<?>) this).getElementList(elementName);
    assertTrue(
        elementList.size() != 0,
        String.format("Список элементов '%s' пустой", elementName));

    return (T) this;
  }

  @Step("Проверка, что элемент со значением '{elementValue}' не существует на странице")
  default T checkElementDoesNotExist(String elementValue) {
    SelenideElement element = $x("//a[.=' " + elementValue + " ']");

    List elements = $$x("//a[.=' " + elementValue + " ']");
    assertEquals(

        elements.size(), 0,
        String.format("Элемент со значением %s существует на странице", elementValue));

    return (T) this;
  }

}