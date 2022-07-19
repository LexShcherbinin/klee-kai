package com.github.lexshcherbinin.kleekai.common.listeners;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.testng.IClassListener;
import org.testng.ITestClass;

/**
 * Установка AllureRestAssured.
 */
public final class SetupApiEnvironmentListener implements IClassListener {

  @Override
  public void onBeforeClass(ITestClass testClass) {
    RestAssured.useRelaxedHTTPSValidation();
    RestAssured.replaceFiltersWith(new AllureRestAssured());
  }

}
