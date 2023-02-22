package com.github.lexshcherbinin.kleekai.api;

import com.github.lexshcherbinin.kleekai.common.ValueStorage;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matcher;

public interface ResponseBodySteps<T extends KleeKaiApiPage<T>> {

  /**
   * Шаг для проверки статус-кода, пришедшего на последний запрос.
   *
   * @param statusCode - ожидаемое значение.
   * @return - возвращает экземпляр текущего api-пейджа.
   */
  @Step("Проверка, что в ответ пришёл код \"{statusCode}\"")
  default T checkStatusCode(int statusCode) {
    int actualValue = ((KleeKaiApiPage<?>) this).getStatusCode();

    Assertions
        .assertThat(actualValue)
        .withFailMessage(String.format("Ожидаемое значение - \"%s\", фактическое - \"%s\"", statusCode, actualValue))
        .isEqualTo(statusCode);

    return (T) this;
  }

  /**
   * Шаг для проверки совпадения значения в response body по указанному пути.
   *
   * @param field - путь\поле.
   * @param value - ожидаемое значение.
   * @return - возвращает экземпляр текущего api-пейджа.
   */
  @Step("Проверка, что поле \"{field}\" содержит значение \"{value}\"")
  default T checkFieldValue(String field, Object value) {
    Object actualValue = ((KleeKaiApiPage<?>) this).getResponse().jsonPath().get(field);

    Assertions
        .assertThat(actualValue)
        .withFailMessage(String.format("Ожидаемое значение - \"%s\", фактическое - \"%s\"", value, actualValue))
        .isEqualTo(value);

    return (T) this;
  }

  /**
   * Шаг для проверки совпадения значения в response body с матчером по указанному пути.
   *
   * @param field   - путь\поле.
   * @param matcher - матчер.
   * @return - возвращает экземпляр текущего api-пейджа.
   */
  @Step("Проверка, что поле \"{field}\" совпадает с матчером \"{matcher}\"")
  default T checkMatcher(String field, Matcher<?> matcher) {
    ((KleeKaiApiPage<?>) this).getResponse().then().body(field, matcher);
    return (T) this;
  }

  /**
   * Шаг для проверки совпадения значения в response body с матчером.
   *
   * @param matcher - матчер.
   * @return - возвращает экземпляр текущего api-пейджа.
   */
  @Step("Проверка, что тело ответа совпадает с матчером \"{matcher}\"")
  default T checkMatcher(Matcher<?> matcher) {
    ((KleeKaiApiPage<?>) this).getResponse().then().body(matcher);
    return (T) this;
  }

  /**
   * Шаг для проверки, что в response body все поля field имеют значение value.
   *
   * @param field - путь\поле.
   * @param value - значение.
   * @return - возвращает экземпляр текущего api-пейджа.
   */
  @Step("Проверка, что в response body все поля \"{field}\" имеют значение \"{value}\"")
  default T checkAllFieldsEqualsValue(String field, Object value) {
    List<Object> actualValueList = ((KleeKaiApiPage<?>) this).getResponse().jsonPath().getList(field);
    Set<Object> set = new HashSet<>(actualValueList);

    Assertions
        .assertThat(set.size() == 1 && set.contains(value))
        .withFailMessage(String.format("В response body не все поля \"%s\" имеют значение \"%s\"", field, value))
        .isTrue();

    return (T) this;
  }

  /**
   * Шаг для проверки, что в response body во всех полях field отсутствует значение value.
   *
   * @param field - путь\поле.
   * @param value - значение.
   * @return - возвращает экземпляр текущего api-пейджа.
   */
  @Step("Проверка, что в response body во всех полях \"{field}\" отсутствует значение \"{value}\"")
  default T checkAllFieldsNotEqualsValue(String field, Object value) {
    List<Object> actualValueList = ((KleeKaiApiPage<?>) this).getResponse().jsonPath().getList(field);

    Assertions
        .assertThat(actualValueList.contains(value))
        .withFailMessage(String.format("В response body в полях \"%s\" присутствует значение \"%s\"", field, value))
        .isTrue();

    return (T) this;
  }

  /**
   * Шаг для сохранения значения из response body.
   *
   * @param field - путь\поле.
   * @param key   - ключ, скоторым значение сохраняется.
   * @return - возвращает экземпляр текущего api-пейджа.
   */
  @Step("Сохранить значение из поля \"{field}\" с ключом \"{key}\"")
  default T saveFieldValue(String field, String key) {
    ValueStorage.saveValue(key, ((KleeKaiApiPage<?>) this).getResponse().jsonPath().get(field));
    return (T) this;
  }

  /**
   * Шаг для сохранения скачанного по последнему запросу файла.
   *
   * @param fileName - имя файла (полный путь с расширением).
   * @return - возвращает экземпляр текущего api-пейджа.
   */
  @Step("Сохранение скачанного файла как \"{fileName}\"")
  default T saveResponseAsFile(String fileName) {
    Response response = ((KleeKaiApiPage<?>) this).getResponse();

    try {
      FileUtils.writeByteArrayToFile(new File(fileName), response.then().extract().asByteArray());

    } catch (IOException e) {
      e.printStackTrace();
    }

    return (T) this;
  }

  /**
   * Шаг для выполнения ожидания.
   *
   * @param seconds - количество секунд.
   * @return - возвращает экземпляр текущего api-пейджа.
   */
  @Step("Выполнено ожидание в течение \"{seconds}\" секунд")
  default T waitForSeconds(long seconds) {
    try {
      Thread.sleep(seconds * 1000);

    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    return (T) this;
  }

}
