package com.github.lexshcherbinin.kleekai.ui.steps;

import static com.codeborne.selenide.Condition.disabled;
import static com.codeborne.selenide.Condition.readonly;
import static com.codeborne.selenide.Selenide.$x;
import static com.github.lexshcherbinin.kleekai.ui.BaseMethods.getDefaultErrorMessage;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.github.lexshcherbinin.kleekai.ui.BaseMethods;
import com.github.lexshcherbinin.kleekai.ui.KleeKaiPage;
import io.qameta.allure.Step;
import java.util.Map;
import java.util.Objects;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.Keys;

/**
 * Шаги для взаимодействия с полями страницы.
 */
public interface FieldSteps<T extends KleeKaiPage<T>> {

  @Step("Выбрать из выпадающего списка \"{elementName}\" значение \"{value}\"")
  default T selectFromDropdownList(String elementName, String value) {
    SelenideElement openValueListButton = ((KleeKaiPage<?>) this).getElement(elementName);
    openValueListButton.click();

    SelenideElement valueElement = $x(String.format("//*[contains(text(), \"%s\")]", value));
    valueElement.should(Condition.exist).scrollTo().click();
    return (T) this;
  }

  @Step("В поле \"{elementName}\" введено значение \"{value}\"")
  default T setFieldValue(String elementName, String value) {
    SelenideElement element = ((KleeKaiPage<?>) this).getElement(elementName);

    try {
      element.sendKeys(value);

    } catch (IllegalArgumentException e) {
      element.setValue(value);
    }

    return (T) this;
  }

  @Step("Заполнить поля в соответствии со списком \"{values}\"")
  default T setFieldsValues(Map<String, String> values) {
    for (String fieldName : values.keySet()) {
      String value = values.get(fieldName);
      setFieldValue(fieldName, value);
    }
    return (T) this;
  }

  @Step("Поле \"{elementName}\" очищено")
  default T clearField(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).clear();
    return (T) this;
  }

  @Step("Поле \"{elementName}\" очищено")
  default T clearWhileFieldNotEmpty(String elementName) {
    SelenideElement valueInput = ((KleeKaiPage<?>) this).getElement(elementName);

    do {
      valueInput
          .shouldNotBe(readonly, disabled)
          .doubleClick()
          .sendKeys(Keys.DELETE);

    } while (valueInput.getValue().length() != 0);

    return (T) this;
  }

  @Step("Проверка, что значение поля \"{elementName}\" равно \"{expectedValue}\"")
  default T checkFieldEqualsValue(String elementName, String expectedValue) {
    SelenideElement element = ((KleeKaiPage<?>) this).getElement(elementName);
    String actualValue = BaseMethods.getAnyElementText(element);

    Assertions
        .assertThat(actualValue)
        .withFailMessage(getDefaultErrorMessage(expectedValue, actualValue))
        .isEqualTo(expectedValue);

    return (T) this;
  }

  @Step("Проверка, что поле \"{elementName}\" содержит значение \"{value}\"")
  default T checkFieldContainsValue(String elementName, String expectedValue) {
    SelenideElement element = ((KleeKaiPage<?>) this).getElement(elementName);
    String actualValue = BaseMethods.getAnyElementText(element);

    Assertions
        .assertThat(actualValue)
        .withFailMessage(getDefaultErrorMessage(expectedValue, actualValue))
        .contains(expectedValue);

    return (T) this;
  }

  @Step("Проверка, что поле \"{elementName}\" пустое")
  default T checkFieldIsEmpty(String elementName) {
    SelenideElement element = ((KleeKaiPage<?>) this).getElement(elementName);
    String actualValue = BaseMethods.getAnyElementText(element);

    Assertions
        .assertThat(actualValue)
        .withFailMessage(getDefaultErrorMessage("", actualValue))
        .isEmpty();

    return (T) this;
  }

  @Step("Проверить, что количество символов в поле \"{elementName}\" равно заданному \"{expectedValue}\"")
  default T checkAmountOfCharInField(String elementName, int expectedValue) {
    SelenideElement element = ((KleeKaiPage<?>) this).getElement(elementName);
    int actualValue = Objects.requireNonNull(element.getValue()).length();

    Assertions
        .assertThat(actualValue)
        .withFailMessage(getDefaultErrorMessage(expectedValue, actualValue))
        .isEqualTo(expectedValue);

    return (T) this;
  }

  @Step("Проверка, что поле \"{elementName}\" доступно для редактирования")
  default T checkFieldIsNotReadonly(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).shouldNot(readonly);
    return (T) this;
  }

  @Step("Проверка, что поле \"{elementName}\" не доступно для редактирования")
  default T checkFieldIsReadonly(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).should(readonly);
    return (T) this;
  }

}