package com.github.lexshcherbinin.kleekai.ui;

/**
 * Применяется в тех местах, где ошибка может быть вызвана указанием неверных тестовых данных или значений.
 */
public class AutotestDataException extends RuntimeException {

  public AutotestDataException(String message) {
    super(message);
  }
}
