package com.github.lexshcherbinin.kleekai.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import java.time.Duration;
import org.awaitility.Awaitility;
import org.awaitility.core.ThrowingRunnable;
import org.hamcrest.Matcher;

/**
 * Основной класс для взаимодействия со страницами.
 *
 * @param <T> - тип страницы
 */
public class BaseApiPage<T extends BaseApiPage<T>> {

  protected Response response;

  public static BaseApiPage<?> getInstance() {
    return new BaseApiPage<>();
  }

  public final <D extends BaseApiPage<D>> D getApiPage(final Class<D> pageClass) {
    assertNotEquals(this.getClass(), pageClass, "Вы уже в этом пейдже");
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
   * @return - возвращает statusCode пришедший последний запрос
   */
  protected int getStatusCode() {
    return response.then().extract().statusCode();
  }

  /**
   * Метод для получения респонса.
   *
   * @return - возвращает response пришедший на последний запрос
   */
  protected Response getResponse() {
    return response;
  }

  /**
   * Метод для приведения тела последнего response к типу apiPage.
   *
   * @param apiPage - класс объектной модели
   * @param <K>     - тип приведения
   * @return - возвращает инстанс объектной модели
   */
  protected <K> K extractAs(Class<K> apiPage) {
    return response.then().extract().as(apiPage);
  }

  /**
   * Шаг для проверки статус-кода, пришедшего на последний запрос.
   *
   * @param statusCode - ожидаемое значение
   * @return - возвращает экземпляр текущего api-пейджа
   */
  @Step("Проверка, что в ответ пришёл код '{statusCode}'")
  public T checkStatusCode(int statusCode) {
    int actualValue = getStatusCode();

    assertEquals(actualValue, statusCode,
        String.format("Ожидаемое значение - '%s', фактическое - '%s'", statusCode, actualValue));

    return (T) this;
  }

  /**
   * Шаг для проверки совпадения значения в body по указанному пути.
   *
   * @param field - путь\поле
   * @param value - ожидаемое значение
   * @return - возвращает экземпляр текущего api-пейджа
   */
  @Step("Проверка, что поле '{field}' содержит значение '{value}'")
  public T checkFieldValue(String field, Object value) {
    Object actualValue = response.jsonPath().get(field);

    assertEquals(actualValue, value,
        String.format("Ожидаемое значение - '%s', фактическое - '%s'", value, actualValue));

    return (T) this;
  }

  /**
   * Шаг для проверки совпадения значения в body с матчером по указанному пути.
   *
   * @param field   - путь\поле
   * @param matcher - матчер
   * @return - возвращает экземпляр текущего api-пейджа
   */
  @Step("Проверка, что поле '{field}' совпадает с матчером '{matcher}'")
  public T checkMatcher(String field, Matcher<?> matcher) {
    response.then().body(field, matcher);
    return (T) this;
  }

  /**
   * Шаг для проверки совпадения значения в body с матчером.
   *
   * @param matcher - матчер
   * @return - возвращает экземпляр текущего api-пейджа
   */
  @Step("Проверка, что тело ответа совпадает с матчером '{matcher}'")
  public T checkMatcher(Matcher<?> matcher) {
    response.then().body(matcher);
    return (T) this;
  }

}