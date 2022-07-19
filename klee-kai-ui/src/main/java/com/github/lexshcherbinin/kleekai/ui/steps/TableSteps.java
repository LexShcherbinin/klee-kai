package com.github.lexshcherbinin.kleekai.ui.steps;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static com.github.lexshcherbinin.kleekai.ui.BaseMethods.getDefaultErrorMessage;
import static org.testng.Assert.assertTrue;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.SelenideElement;
import com.github.lexshcherbinin.kleekai.ui.BaseMethods;
import com.github.lexshcherbinin.kleekai.ui.KleeKaiPage;
import io.qameta.allure.Step;
import java.util.List;
import org.assertj.core.api.Assertions;

/**
 * Шаги для взаимодействия с таблицами.
 */
public interface TableSteps<T extends KleeKaiPage<T>> {

  String getCellXpath(String column, int row);

  String getColumnXpath(String column);

  @Step("Выполнен клик по ячейке в колонке \"{column}\" в строке \"{row}\"")
  default T clickCell(String column, int row) {
    $x(getCellXpath(column, row)).click();
    return (T) this;
  }

  @Step("В ячейку в колонке \"{column}\" в строке \"{row}\" введено значение \"{value}\"")
  default T fillCell(String column, int row, String value) {
    $x(getCellXpath(column, row) + "//input").sendKeys(value);
    return (T) this;
  }

  @Step("Выполнено наведение на ячейку в колонке \"{column}\" в строке \"{row}\"")
  default T hoverCell(String column, int row) {
    $x(getCellXpath(column, row)).hover();
    return (T) this;
  }

  @Step("Выполнена очистка ячейки в колонке \"{column}\" в строке \"{row}\"")
  default T clearCell(String column, int row) {
    $x(getCellXpath(column, row) + "//input").clear();
    return (T) this;
  }

  @Step("Выполнен клик по кнопке \"{name}\"")
  default T clickButton(String name) {
    ((KleeKaiPage<?>) this).getElement(name).click();
    return (T) this;
  }

  @Step("Проверка, что в колонке \"{column}\" содержится список значений \"{expectedValueList}\"")
  default T checkColumnContainsValueList(String column, List<String> expectedValueList) {
    $$x(getColumnXpath(column)).should(CollectionCondition.containExactTextsCaseSensitive(expectedValueList));
    return (T) this;
  }

  @Step("Проверка, что ячейка в колонке \"{column}\" в строке \"{row}\" содержит значение \"{expectedValue}\"")
  default T checkCellContainsValue(String column, int row, String expectedValue) {
    SelenideElement cell = $x(getCellXpath(column, row));
    String actualValue = BaseMethods.getAnyElementText(cell);

    Assertions
        .assertThat(actualValue)
        .withFailMessage(String.format("Фактическое значение %s не содержит ожидаемое %s", actualValue, expectedValue))
        .contains(expectedValue);

    return (T) this;
  }

  @Step("Проверка, что значение ячейки в колонке \"{column}\" в строке \"{row}\" равно \"{expectedValue}\"")
  default T checkCellEqualsValue(String column, int row, String expectedValue) {
    SelenideElement cell = $x(getCellXpath(column, row));
    String actualValue = BaseMethods.getAnyElementText(cell);

    Assertions
        .assertThat(actualValue)
        .withFailMessage(getDefaultErrorMessage(expectedValue, actualValue))
        .isEqualTo(expectedValue);

    return (T) this;
  }

  @Step("Проверка, что ячейка в колонке \"{column}\" в строке \"{row}\" пустая")
  default T checkCellIsEmpty(String column, int row) {
    checkCellEqualsValue(column, row, "");
    return (T) this;
  }

}