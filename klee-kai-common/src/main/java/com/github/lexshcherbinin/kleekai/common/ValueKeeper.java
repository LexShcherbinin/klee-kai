package com.github.lexshcherbinin.kleekai.common;

import java.util.HashMap;
import java.util.Map;

/**
 * Реализация сохранения значений.
 */
public class ValueKeeper {

  private static final ThreadLocal<ValueKeeper> varThread = ThreadLocal.withInitial(ValueKeeper::new);

  private final Map<String, Object> variables = new HashMap<>();

  private static ValueKeeper getInstance() {
    return varThread.get();
  }

  public static void saveValue(String key, Object value) {
    getInstance().variables.put(key, value);
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
    return getInstance().variables.get(name).toString();
  }

  /**
   * Возвращает сохранённое значение.
   *
   * @param name - ключ значения
   * @return - возвращает сохранённое значение типа Integer
   */
  public static Integer getInteger(String name) {
    Object value = getInstance().variables.get(name);

    if (value instanceof String) {
      return Integer.parseInt(value.toString());
    }

    return (Integer) getInstance().variables.get(name);
  }

  /**
   * Возвращает сохранённое значение.
   *
   * @param name - ключ значения
   * @return - возвращает сохранённое значение типа Double
   */
  public static Double getDouble(String name) {
    Object value = getInstance().variables.get(name);

    if (value instanceof String) {
      return Double.parseDouble(value.toString());
    }

    return (Double) getInstance().variables.get(name);
  }

  public static void clear() {
    getInstance().variables.clear();
  }

  public static Object remove(String key) {
    return getInstance().variables.remove(key);
  }

}
