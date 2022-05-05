package com.github.lexshcherbinin.kleekai.api;

import io.restassured.response.Response;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Фабрика для создания api-пейджей.
 */
public class ApiPageFactory {

  /**
   * Создаём инстанс страницы.
   *
   * @param apiPageClass - имя страницы
   * @param response     - сохраняемый респонс
   * @param <T>          - тип страницы
   * @return - возвращает инстанс созданной страницы
   */
  public <T extends KleeKaiApiPage<T>> T createApiPage(Class<T> apiPageClass, Response response) {
    try {
      Class<KleeKaiApiPage<?>> superClass = (Class<KleeKaiApiPage<?>>) apiPageClass.getSuperclass();
      Constructor<T> constructor = apiPageClass.getConstructor();
      constructor.setAccessible(true);
      T instance = constructor.newInstance();

      Field field = superClass.getDeclaredField("response");
      field.setAccessible(true);
      field.set(instance, response);

      return constructor.newInstance();

    } catch (InstantiationException | IllegalAccessException | InvocationTargetException
        | NoSuchMethodException | NoSuchFieldException e) {
      e.printStackTrace();

      throw new PageNotInitializedException("Не удалось инициализировать " + apiPageClass.getName());
    }
  }

  static class PageNotInitializedException extends RuntimeException {

    PageNotInitializedException(String message) {
      super(message);
    }
  }
}