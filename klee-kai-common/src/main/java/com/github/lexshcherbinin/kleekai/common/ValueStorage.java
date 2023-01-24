package com.github.lexshcherbinin.kleekai.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Реализация сохранения значений.
 */
public final class ValueStorage {

  private static final ThreadLocal<ValueStorage> varThread = ThreadLocal.withInitial(ValueStorage::new);

  private final Map<String, Object> variables = new HashMap<>();

  private ValueStorage() {

  }

  private static ValueStorage getInstance() {
    return varThread.get();
  }

  public static void clear() {
    getInstance().variables.clear();
  }

  public static Object remove(String key) {
    return getInstance().variables.remove(key);
  }

  public static void saveValue(String name, Object value) {
    getInstance().variables.put(name, value);
  }

  public static Object getValue(String name) {
    return getInstance().variables.get(name);
  }

  /**
   * Возвращает сохранённое значение.
   *
   * @param name - ключ значения
   * @return - возвращает сохранённое значение типа String
   */
  public static String getString(String name) {
    Object value = getValue(name);

    if (value != null) {
      return value.toString();

    } else {
      return null;
    }
  }

  /**
   * Возвращает сохранённое значение.
   *
   * @param name - ключ значения
   * @return - возвращает сохранённое значение типа Integer
   */
  public static Integer getInteger(String name) {
    Object value = getValue(name);

    if (value != null) {
      if (value instanceof String) {
        return Integer.parseInt(value.toString());

      } else {
        return (Integer) value;
      }

    } else {
      return null;
    }
  }

  /**
   * Возвращает сохранённое значение.
   *
   * @param name - ключ значения
   * @return - возвращает сохранённое значение типа Double
   */
  public static Double getDouble(String name) {
    Object value = getValue(name);

    if (value != null) {
      if (value instanceof String) {
        return Double.parseDouble(value.toString());

      } else {
        return (Double) value;
      }

    } else {
      return null;
    }
  }

}
