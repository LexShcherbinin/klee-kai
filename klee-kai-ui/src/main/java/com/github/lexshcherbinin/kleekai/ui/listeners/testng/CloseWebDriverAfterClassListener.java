package com.github.lexshcherbinin.kleekai.ui.listeners.testng;

import com.codeborne.selenide.WebDriverRunner;
import com.github.lexshcherbinin.kleekai.ui.BaseMethods;
import org.testng.IClassListener;
import org.testng.ITestClass;

/**
 * Очистка всех cookies и закрытие браузера после прогона всех тестов CloseWebDriverAfterClassListener нужен только в тех тестах,
 * где до нужной страницы надо долго добираться и только потом там прогонять несколько тестов, (чтобы после первого упавшего не
 * проходить до этой страницы заново)
 */
public final class CloseWebDriverAfterClassListener implements IClassListener {

  @Override
  public void onAfterClass(ITestClass testClass) {
    if (WebDriverRunner.hasWebDriverStarted()) {
      BaseMethods.deleteAllCookies();
      BaseMethods.closeWebDriver();
    }
  }

}