package com.github.lexshcherbinin.kleekai.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.assertj.core.api.Assertions;

public interface ResponseBodySteps<T extends KleeKaiApiPage<T>> {

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

  @Step("Проверка, что в response body во всех полях \"{field}\" отсутствует значение \"{value}\"")
  default T checkAllFieldsNotEqualsValue(String field, Object value) {
    List<Object> actualValueList = ((KleeKaiApiPage<?>) this).getResponse().jsonPath().getList(field);

    Assertions
        .assertThat(actualValueList.contains(value))
        .withFailMessage(String.format("В response body в полях \"%s\" присутствует значение \"%s\"", field, value))
        .isTrue();

    return (T) this;
  }

}
