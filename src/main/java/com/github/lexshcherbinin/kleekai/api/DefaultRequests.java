package com.github.lexshcherbinin.kleekai.api;

import static io.restassured.RestAssured.given;
import static java.util.Objects.requireNonNullElse;

import com.github.lexshcherbinin.kleekai.helpers.ValueKeeper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.Map;

/**
 * Запросы по умолчанию.
 */
public class DefaultRequests {

  public static final Map<String, Object> DEFAULT_HEADERS = Map.of(
      "encoding", "UTF-8",
      "Content-Type", ContentType.JSON.toString()
  );

  /**
   * Отправляет POST-запрос с дефолтными параметрами.
   *
   * @param params - параметры запроса
   * @param url    - url запроса
   * @return - возвращает ответ (Response) на отправленный запрос
   */
  public static Response defaultPost(Map<String, Object> params, String url) {
    return given()
        .headers(DEFAULT_HEADERS)
        .header("authorization", ValueKeeper.getValue("accessToken"))
        .params(requireNonNullElse(params, Map.of()))
        .post(url);
  }

  /**
   * Отправляет POST-запрос с дефолтными параметрами.
   *
   * @param params - параметры запроса
   * @param body   - тело запроса
   * @param url    - url запроса
   * @return - возвращает ответ (Response) на отправленный запрос
   */
  public static Response defaultPost(Map<String, Object> params, Object body, String url) {
    return given()
        .headers(DEFAULT_HEADERS)
        .header("authorization", ValueKeeper.getValue("accessToken"))
        .params(requireNonNullElse(params, Map.of()))
        .body(body)
        .post(url);
  }

  /**
   * Отправляет GET-запрос с дефолтными параметрами.
   *
   * @param params - параметры запроса
   * @param body   - тело запроса
   * @param url    - url запроса
   * @return - возвращает ответ (Response) на отправленный запрос
   */
  public static Response defaultGet(Map<String, Object> params, Object body, String url) {
    return given()
        .headers(DEFAULT_HEADERS)
        .header("authorization", ValueKeeper.getValue("accessToken"))
        .params(requireNonNullElse(params, Map.of()))
        .body(body)
        .get(url);
  }

  /**
   * Отправляет GET-запрос с дефолтными параметрами.
   *
   * @param params - параметры запроса
   * @param url    - url запроса
   * @return - возвращает ответ (Response) на отправленный запрос
   */
  public static Response defaultGet(Map<String, Object> params, String url) {
    return given()
        .headers(DEFAULT_HEADERS)
        .header("authorization", ValueKeeper.getValue("accessToken"))
        .params(requireNonNullElse(params, Map.of()))
        .get(url);
  }

  /**
   * Отправляет PUT-запрос с дефолтными параметрами.
   *
   * @param params - параметры запроса
   * @param body   - тело запроса
   * @param url    - url запроса
   * @return - возвращает ответ (Response) на отправленный запрос
   */
  public static Response defaultPut(Map<String, Object> params, Object body, String url) {
    return given()
        .headers(DEFAULT_HEADERS)
        .header("authorization", ValueKeeper.getValue("accessToken"))
        .params(requireNonNullElse(params, Map.of()))
        .body(body)
        .put(url);
  }

  /**
   * Отправляет PUT-запрос с дефолтными параметрами.
   *
   * @param params - параметры запроса
   * @param url    - url запроса
   * @return - возвращает ответ (Response) на отправленный запрос
   */
  public static Response defaultPut(Map<String, Object> params, String url) {
    return given()
        .headers(DEFAULT_HEADERS)
        .header("authorization", ValueKeeper.getValue("accessToken"))
        .params(requireNonNullElse(params, Map.of()))
        .put(url);
  }

  /**
   * Отправляет DELETE-запрос с дефолтными параметрами.
   *
   * @param params - параметры запроса
   * @param body   - тело запроса
   * @param url    - url запроса
   * @return - возвращает ответ (Response) на отправленный запрос
   */
  public static Response defaultDelete(Map<String, Object> params, Object body, String url) {
    return given()
        .headers(DEFAULT_HEADERS)
        .header("authorization", ValueKeeper.getValue("accessToken"))
        .params(requireNonNullElse(params, Map.of()))
        .body(body)
        .delete(url);
  }

  /**
   * Отправляет DELETE-запрос с дефолтными параметрами.
   *
   * @param params - параметры запроса
   * @param url    - url запроса
   * @return - возвращает ответ (Response) на отправленный запрос
   */
  public static Response defaultDelete(Map<String, Object> params, String url) {
    return given()
        .headers(DEFAULT_HEADERS)
        .header("authorization", ValueKeeper.getValue("accessToken"))
        .params(requireNonNullElse(params, Map.of()))
        .delete(url);
  }

  /**
   * Отправляет PATCH-запрос с дефолтными параметрами.
   *
   * @param params - параметры запроса
   * @param body   - тело запроса
   * @param url    - url запроса
   * @return - возвращает ответ (Response) на отправленный запрос
   */
  public static Response defaultPatch(Map<String, Object> params, Object body, String url) {
    return given()
        .headers(DEFAULT_HEADERS)
        .header("authorization", ValueKeeper.getValue("accessToken"))
        .params(requireNonNullElse(params, Map.of()))
        .body(body)
        .patch(url);
  }

  /**
   * Отправляет PATCH-запрос с дефолтными параметрами.
   *
   * @param params - параметры запроса
   * @param url    - url запроса
   * @return - возвращает ответ (Response) на отправленный запрос
   */
  public static Response defaultPatch(Map<String, Object> params, String url) {
    return given()
        .headers(DEFAULT_HEADERS)
        .header("authorization", ValueKeeper.getValue("accessToken"))
        .params(requireNonNullElse(params, Map.of()))
        .patch(url);
  }

}
