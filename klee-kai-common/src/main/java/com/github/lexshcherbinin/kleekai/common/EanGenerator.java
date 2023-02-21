package com.github.lexshcherbinin.kleekai.common;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.RandomStringUtils;

public final class EanGenerator {

  private EanGenerator() {

  }

  /**
   * Генерация EAN13-кода.
   *
   * @return - возвращает рандомно сгенерированный EAN13-код.
   */
  public static String getEan13() {
    String random = RandomStringUtils.randomNumeric(12);
    return getEan13(random);
  }

  /**
   * Генерация EAN13-кода.
   *
   * @param value - 12-значное число, к которому нужно добавить 13-й знак.
   * @return - возвращает EAN13-код.
   */
  public static String getEan13(String value) {
    if (value.toCharArray().length != 12) {
      throw new IllegalArgumentException(String.format("Неверное значение аргумента: \"%s\"", value));
    }

    List<Integer> randomList = value.chars()
        .mapToObj(e -> (char) e)
        .map(Character::getNumericValue)
        .collect(Collectors.toList());

    int sum = 0;
    for (int i = 0; i < value.length(); i++) {
      sum = sum + ((randomList.get(i)) * (i % 2 == 0 ? 1 : 3));
    }

    int checkDigit = sum % 10 == 0 ? 0 : (10 - (sum % 10));
    return value + checkDigit;
  }

  /**
   * Генерация UPC-A (двенадцатизначного) штрих кода.
   *
   * @return - возвращает рандомно сгенерированный UPC-A (двенадцатизначный) штрих код.
   */
  public static String getUpcA() {
    String random = RandomStringUtils.randomNumeric(11);
    return getUpcA(random);
  }

  /**
   * Генерация UPC-A (двенадцатизначного) штрих кода.
   *
   * @param value - 11-значное число, к которому нужно добавить 12-й знак.
   * @return - возвращает UPC-A (двенадцатизначный) штрих код.
   */
  public static String getUpcA(String value) {
    if (value.toCharArray().length != 12) {
      throw new IllegalArgumentException(String.format("Неверное значение аргумента: \"%s\"", value));
    }

    int evenSum = 0;
    int oddSum = 0;

    for (int i = 1; i <= value.length(); i++) {
      int number = Character.getNumericValue(value.charAt(i - 1));

      if (i % 2 == 0) {
        evenSum += number;

      } else {
        oddSum += number;
      }
    }

    int resultNumber = (10 - ((oddSum * 3 + evenSum) % 10)) % 10;
    return value + resultNumber;
  }

}
