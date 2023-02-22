package com.github.lexshcherbinin.kleekai.api;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import org.awaitility.Awaitility;
import org.awaitility.core.ThrowingRunnable;

/**
 * Основной класс для взаимодействия со страницами.
 *
 * @param <T> - тип страницы
 */
public class KleeKaiApiPage<T extends KleeKaiApiPage<T>> implements ResponseBodySteps<T> {

  protected Response response;

  public static KleeKaiApiPage<?> getInstance() {
    return new KleeKaiApiPage<>();
  }

  public final <D extends KleeKaiApiPage<D>> D getApiPage(final Class<D> pageClass) {
    if (this.getClass().equals(pageClass)) {
      throw new IllegalArgumentException("Вы уже находитесь в пейдже " + pageClass.getSimpleName());
    }

    return new ApiPageFactory().createApiPage(pageClass, response);
  }

  protected void untilAsserted(ThrowingRunnable assertion) {
    Awaitility.await()
        .atMost(Duration.ofMinutes(1))
        .pollInSameThread()
        .pollInterval(Duration.ofSeconds(10))
        .untilAsserted(assertion);
  }

  /**
   * Метод для получения статус-кода ответа.
   *
   * @return - возвращает statusCode пришедший последний запрос.
   */
  protected int getStatusCode() {
    return response.then().extract().statusCode();
  }

  /**
   * Метод для получения респонса.
   *
   * @return - возвращает response пришедший на последний запрос.
   */
  protected Response getResponse() {
    return response;
  }

  /**
   * Метод для приведения тела последнего response к типу apiPage.
   *
   * @param apiPage - класс объектной модели.
   * @param <K>     - тип приведения.
   * @return - возвращает инстанс объектной модели.
   */
  protected <K> K extractAs(Class<K> apiPage) {
    return response.then().extract().as(apiPage);
  }

  /**
   * Прикрепление excel-файла к отчёту.
   *
   * @param name - имя файла.
   * @param path - путь до файла.
   */
  protected void attachExelFile(String name, String path) {
    try {
      Allure.getLifecycle().addAttachment(name, "", "xlsx", Files.readAllBytes(Paths.get(path)));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}