package com.github.lexshcherbinin.kleekai.steps;

import static com.codeborne.selenide.Condition.disabled;
import static com.codeborne.selenide.Condition.readonly;
import static com.codeborne.selenide.Selenide.$x;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.github.lexshcherbinin.kleekai.ui.BaseMethods;
import com.github.lexshcherbinin.kleekai.ui.BasePage;
import io.qameta.allure.Step;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Objects;
import org.openqa.selenium.Keys;

/**
 * Шаги для взаимодействия с полями страницы
 */
public interface FieldSteps<T extends BasePage<T>> {

  /**
   * Возвращает локатор элемента, отвечающий определённому значению value из выпадающего списка
   */
  default String getValueXpath(String value) {
    return String.format("//*[contains(text(), '%s')]", value);
  }

  @Step("Выбрать из выпадающего списка '{elementName}' значение '{value}'")
  default T selectFromDropdownList(String elementName, String value) {
    SelenideElement openValueListButton = ((BasePage<?>) this).getElement(elementName);
    openValueListButton.click();

    SelenideElement valueElement = $x(getValueXpath(value));
    valueElement.should(Condition.exist).scrollTo().click();
//    valueElement.should(Condition.hidden);
    return (T) this;
  }

  //TODO: Между setValue и sendKeys всё-таки есть различия. Разобраться, чем они отличаются и сделать соответствующие методы
  @Step("В поле '{elementName}' введено значение '{value}'")
  default T setFieldValue(String elementName, String value) {
    SelenideElement element = ((BasePage<?>) this).getElement(elementName);
//    element.setValue(value);
//    element.sendKeys(value);
//    element.val(value);

    try {
      element.sendKeys(value);

    } catch (IllegalArgumentException e) {
      element.setValue(value);
    }

    return (T) this;
  }

  //TODO: Чем отличается от setFieldValue и зачем нужен?
  @Deprecated
  @Step("В поле '{elementName}' введено значение '{value}'")
  default T sendKeysToField(String elementName, String value) {
    SelenideElement element = ((BasePage<?>) this).getElement(elementName);
    element.sendKeys(value);

    return (T) this;
  }

  @Step("Заполнить поля в соответствии со списком '{values}'")
  default T setFieldsValues(Map<String, String> values) {
    for (String fieldName : values.keySet()) {
      String value = values.get(fieldName);
      setFieldValue(fieldName, value);
    }
    return (T) this;
  }

  @Step("Поле '{elementName}' очищено")
  default T cleanField(String elementName) {
    SelenideElement valueInput = ((BasePage<?>) this).getElement(elementName);

    do {
      valueInput.shouldNotBe(readonly, disabled).doubleClick().sendKeys(Keys.DELETE);

    } while (valueInput.getValue().length() != 0);

    return (T) this;
  }

  @Step("В поле '{fieldName}' введена текущая дате в формате '{value}'")
  default T setFieldCurrentDate(String fieldName, String dateFormat) {
    long date = System.currentTimeMillis();
    String currentStringDate;

    try {
      currentStringDate = new SimpleDateFormat(dateFormat).format(date);

    } catch (IllegalArgumentException ex) {
      currentStringDate = new SimpleDateFormat("dd.MM.yyyy").format(date);
    }

    SelenideElement valueInput = ((BasePage<?>) this).getElement(fieldName);
    valueInput.setValue(currentStringDate);
    return (T) this;
  }

  @Step("Проверка, что значение поля '{elementName}' равно '{value}'")
  default T checkFieldEqualsValue(String elementName, String expectedValue) {
    SelenideElement element = ((BasePage<?>) this).getElement(elementName);
    String actualValue = BaseMethods.getAnyElementText(element);

    assertEquals(
        actualValue, expectedValue,
        BaseMethods.getDefaultErrorMessage(expectedValue, actualValue));

    return (T) this;
  }

  @Step("Проверка, что поле '{elementName}' содержит значение '{value}'")
  default T checkFieldContainsValue(String elementName, String expectedValue) {
    SelenideElement element = ((BasePage<?>) this).getElement(elementName);
    String actualValue = BaseMethods.getAnyElementText(element);

    assertTrue(
        actualValue.contains(expectedValue),
        String.format("Поле '%s' не содержит значение '%s'", elementName, expectedValue));

    return (T) this;
  }

  @Step("Проверка, что поле '{elementName}' пустое")
  default T checkFieldIsEmpty(String elementName) {
    SelenideElement element = ((BasePage<?>) this).getElement(elementName);
    String actualValue = BaseMethods.getAnyElementText(element);

    assertEquals(
        actualValue, "",
        String.format("Поле '%s' не пустое", elementName));

    return (T) this;
  }

  @Step("Проверить, что количество символов в поле '{elementName}' равно заданному '{maxChars}'")
  default T checkAmountOfCharInField(String elementName, int maxChars) {
    SelenideElement element = ((BasePage<?>) this).getElement(elementName);
    int length = Objects.requireNonNull(element.getValue()).length();

    assertEquals(
        length, maxChars,
        String.format("Поле '%s' должно содержать '%s' символов, но содержит '%s' символов", elementName, maxChars, length));

    return (T) this;
  }

  @Step("Проверка, что поле '{elementName}' доступно для редактирования")
  default T checkFieldIsNotReadonly(String elementName) {
    SelenideElement element = ((BasePage<?>) this).getElement(elementName);

    assertFalse(
        element.is(readonly),
        String.format("Поле '%s' не доступно для редактирования", elementName));

    return (T) this;
  }

  @Step("Проверка, что поле '{elementName}' не доступно для редактирования")
  default T checkFieldIsReadonly(String elementName) {
    SelenideElement element = ((BasePage<?>) this).getElement(elementName);

    assertTrue(
        element.is(readonly),
        String.format("Поле '%s' доступно для редактирования", elementName));

    return (T) this;
  }
}