package com.github.lexshcherbinin.kleekai.common;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.testng.IInvokedMethod;
import org.testng.ITestClass;
import org.testng.ITestNGListener;
import org.testng.annotations.Listeners;

public final class ListenersHelper {

  /**
   * Проверка, что вызванный тестовый метод содержит указанный листенер.
   *
   * @param listener - указанный листенер.
   * @param method   - вызванный тестовый метод.
   * @return - возвращает true, если тестовый класс аннотирован аннотацией Listeners, и в ней присутствует указанный листенер.
   */
  public static boolean checkTestContainsListener(Class<?> listener, IInvokedMethod method) {
    List<Class<? extends ITestNGListener>[]> listenerList = Arrays.stream(
            method
                .getTestMethod()
                .getConstructorOrMethod()
                .getDeclaringClass()
                .getDeclaredAnnotations()
        )
        .filter(annotation -> annotation.annotationType().getName().equals(Listeners.class.getName()))
        .map(annotation -> ((Listeners) annotation).value())
        .collect(Collectors.toList());

    if (listenerList.isEmpty()) {
      return false;

    } else {
      return Arrays.asList(listenerList.get(0)).contains(listener);
    }
  }

  /**
   * Проверка, что вызванный тестовый класс содержит указанный листенер.
   *
   * @param listener   - указанный листенер.
   * @param testClass- вызванный тестовый класс.
   * @return - возвращает true, если тестовый класс аннотирован аннотацией Listeners, и в ней присутствует указанный листенер.
   */
  public static boolean checkTestContainsListener(Class<?> listener, ITestClass testClass) {
    List<Class<? extends ITestNGListener>[]> listenerList = Arrays.stream(
            testClass
                .getRealClass()
                .getDeclaredAnnotations()
        )
        .filter(annotation -> annotation.annotationType().getName().equals(Listeners.class.getName()))
        .map(annotation -> ((Listeners) annotation).value())
        .collect(Collectors.toList());

    if (listenerList.isEmpty()) {
      return false;

    } else {
      return Arrays.asList(listenerList.get(0)).contains(listener);
    }
  }

}