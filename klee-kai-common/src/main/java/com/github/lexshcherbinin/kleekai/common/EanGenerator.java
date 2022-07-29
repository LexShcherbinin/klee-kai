package com.github.lexshcherbinin.kleekai.common;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.RandomStringUtils;

public final class EanGenerator {

  private EanGenerator() {

  }

  /**
   * Генерация EAN13-код.
   *
   * @return - возвращает рандомно сгенерированный EAN13-код.
   */
  public static String getEan13() {
    String random = RandomStringUtils.randomNumeric(12);
    List<Integer> randomList = random.chars()
        .mapToObj(e -> (char) e)
        .map(Character::getNumericValue)
        .collect(Collectors.toList());

    int sum = 0;
    for (int i = 0; i < random.length(); i++) {
      sum = sum + ((randomList.get(i)) * (i % 2 == 0 ? 1 : 3));
    }

    int checkDigit = sum % 10 == 0 ? 0 : (10 - (sum % 10));
    return random + checkDigit;
  }

  /**
   * Генерация UPC-A (двенадцатизначный) штрих кода.
   *
   * @return - возвращает рандомно сгенерированный UPC-A (двенадцатизначный) штрих код.
   */
  public static String getUpcA() {
    String random = RandomStringUtils.randomNumeric(11);

    int evenSum = 0;
    int oddSum = 0;

    for (int i = 1; i <= random.length(); i++) {
      int number = Character.getNumericValue(random.charAt(i - 1));

      if (i % 2 == 0) {
        evenSum += number;

      } else {
        oddSum += number;
      }
    }

    int resultNumber = (10 - ((oddSum * 3 + evenSum) % 10)) % 10;
    return random + resultNumber;
  }

}
