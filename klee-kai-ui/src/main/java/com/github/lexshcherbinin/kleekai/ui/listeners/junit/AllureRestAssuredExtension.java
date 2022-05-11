package com.github.lexshcherbinin.kleekai.ui.listeners.junit;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Установка AllureRestAssured
 */
public final class AllureRestAssuredExtension implements BeforeAllCallback {

  @Override
  public void beforeAll(ExtensionContext context) {
    RestAssured.useRelaxedHTTPSValidation();
    RestAssured.replaceFiltersWith(new AllureRestAssured());
  }

}