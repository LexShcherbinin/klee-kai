package com.github.lexshcherbinin.kleekai.api;

import com.github.lexshcherbinin.kleekai.common.ValueStorage;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import java.time.Duration;
import org.assertj.core.api.Assertions;
import org.awaitility.Awaitility;
import org.awaitility.core.ThrowingRunnable;
import org.hamcrest.Matcher;

/**
 * Основной класс для взаимодействия со страницами.
 *
 * @param <T> - тип страницы
 */
public class KleeKaiApiPage<T extends KleeKaiApiPage<T>> {

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

    Assertions
        .assertThat(actualValue)
        .withFailMessage(String.format("Ожидаемое значение - '%s', фактическое - '%s'", statusCode, actualValue))
        .isEqualTo(statusCode);

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

    Assertions
        .assertThat(actualValue)
        .withFailMessage(String.format("Ожидаемое значение - '%s', фактическое - '%s'", value, actualValue))
        .isEqualTo(value);

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

  @Step("Сохранить значение из поля {field} с ключём {key}")
  public T saveFieldValue(String field, String key) {
    ValueStorage.saveValue(key, response.jsonPath().get(field));
    return (T) this;
  }

}