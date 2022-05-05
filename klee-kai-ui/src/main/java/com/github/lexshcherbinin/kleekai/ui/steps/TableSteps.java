package com.github.lexshcherbinin.kleekai.ui.steps;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.github.lexshcherbinin.kleekai.ui.BaseMethods;
import com.github.lexshcherbinin.kleekai.ui.KleeKaiPage;
import io.qameta.allure.Step;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Шаги для взаимодействия с таблицами. Требует реализации методов: - getCellXpath(String column, int row) - getColumnXpath(String
 * column)
 */
public interface TableSteps<T extends KleeKaiPage<T>> {

  String getCellXpath(String column, int row);

  String getColumnXpath(String column);

  @Step("Выполнен клик по ячейке в колонке '{column}' в строке '{row}'")
  default T clickCell(String column, int row) {
    SelenideElement cell = $x(getCellXpath(column, row));
    cell.click();
    return (T) this;
  }

  @Step("В ячейку в колонке '{column}' в строке '{row}' введено значение '{value}'")
  default T fillCell(String column, int row, String value) {
    SelenideElement cell = $x(getCellXpath(column, row) + "//input");
    cell.setValue(value);

    return (T) this;
  }

  @Step("Выполнено наведение на ячейку в колонке '{column}' в строке '{row}'")
  default T hoverCell(String column, int row) {
    SelenideElement cell = $x(getCellXpath(column, row));
    cell.hover();

    return (T) this;
  }

  @Step("Ячейка в колонке '{column}' в строке '{row}' очищена")
  default T clearCell(String column, int row) {
    SelenideElement cell = $x(getCellXpath(column, row) + "//input");
    cell.clear();

    return (T) this;
  }

  @Step("Выполнен клик по кнопке '{name}'")
  default T clickButton(String name) {
    ((KleeKaiPage<?>) this).getElement(name).click();
    return (T) this;
  }

  @Step("Проверка, что в колонке '{column}' содержится список значений '{expectedValueList}'")
  default T checkColumnContainsValueList(String column, List<String> expectedValueList) {
    List<SelenideElement> elementList = $$x(getColumnXpath(column));

    List<String> actualValueList = elementList.stream()
        .map(SelenideElement::getValue)
        .collect(Collectors.toList());

    assertTrue(
        actualValueList.contains(expectedValueList),
        String.format("Актуальный список значений '%s' не содержит в себе значения '%s'", actualValueList, expectedValueList));

    return (T) this;
  }

  @Step("Проверка, что значение ячейки в колонке '{column}' в строке '{row}' равно '{expectedValue}'")
  default T checkCellEqualsValue(String column, int row, String expectedValue) {
    SelenideElement cell = $x(getCellXpath(column, row));
    String actualValue = BaseMethods.getAnyElementText(cell);

    assertEquals(
        actualValue, expectedValue,
        String.format("Ячейка в колонке '%s' в строке '%s' ожидалось значение '%s', а получилось '%s'", column, row,
            expectedValue,
            actualValue));

    return (T) this;
  }

//  @Step("Проверка, что элемент со значением '{elementValue}' не существует на странице")
//  default T checkElementDoesNotExist(String elementValue) {
//    SelenideElement element = $x("//a[.=' "+elementValue+" ']");
//
//
//    List elements = $$x("//a[.=' "+elementValue+" ']");
//    assertEquals(
//
//        elements.size(), 0,
//        String.format("Элемент со значением %s существует на странице", elementValue));
//
//    return (T) this;
//  }

//  @Step("Проверка, что в колонке '{column}' отсутствует ячейка со значением '{expectedValue}'")
//  default T checkColumnDoesNotContainValue(String column, String expectedValue) {
//    String xpath = getColumnXpath(column);
//
//    List<String> actualList = Selenide.$$x(xpath)
//        .stream()
//        .map(SelenideElement::getText)
//        .collect(Collectors.toList());
//
//    assertFalse(actualList.contains(expectedValue),   String.format("В колонке %s есть ячейка со значением %s",column,expectedValue));
//    return (T) this;

//assertFalse(actualList.stream().anyMatch(value -> value.equals(expectedValue)),   String.format("В колонке %s есть ячейка со значением %s",column,expectedValue));
//    assertTrue(
//        actualValueList.stream().allMatch(value -> value.equals(expectedValue)),
//        String.format("Не все значения в колонке %s совпадают с %s", column, expectedValue));

//}

  @Step("Проверка, что ячейка в колонке '{column}' в строке '{row}' содержит значение '{expectedValue}'")
  default T checkCellContainsValue(String column, int row, String expectedValue) {
    SelenideElement cell = $x(getCellXpath(column, row));
    String actualValue = BaseMethods.getAnyElementText(cell);

    assertTrue(
        actualValue.contains(expectedValue),
        String.format("Ячейка в колонке '%s' в строке '%s' не содержит значение '%s'", column, row, expectedValue));

    return (T) this;
  }

  @Step("Проверка, что ячейка в колонке '{column}' в строке '{row}' пустая")
  default T checkCellIsEmpty(String column, int row) {
    SelenideElement cell = $x(getCellXpath(column, row));
    String actualValue = BaseMethods.getAnyElementText(cell);

    assertEquals(
        actualValue, "",
        String.format("Ячейка в колонке '%s' в строке '%s' не пустое", column, row));

    return (T) this;
  }

  @Step("Проверка, что сообщение '{text}' отображается на странице")
  default T checkMessageIsDisplayed(String text) {
    SelenideElement message = ((KleeKaiPage<?>) this).getElement(text);
    assertTrue(
        message.shouldBe(Condition.visible).isDisplayed(),
        String.format("Сообщение '%s' не отображается на странице", message));

    return (T) this;
  }

}