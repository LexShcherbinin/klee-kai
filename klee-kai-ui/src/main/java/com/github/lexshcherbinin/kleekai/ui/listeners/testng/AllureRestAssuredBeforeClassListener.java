package com.github.lexshcherbinin.kleekai.ui.listeners.testng;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.testng.IClassListener;
import org.testng.ITestClass;

/**
 * Установка AllureRestAssured
 */
public final class AllureRestAssuredBeforeClassListener implements IClassListener {

  @Override
  public void onBeforeClass(ITestClass testClass) {
    RestAssured.useRelaxedHTTPSValidation();
    RestAssured.replaceFiltersWith(new AllureRestAssured());
  }

}