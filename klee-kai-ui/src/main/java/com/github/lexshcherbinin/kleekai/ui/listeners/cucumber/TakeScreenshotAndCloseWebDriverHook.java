package com.github.lexshcherbinin.kleekai.ui.listeners.cucumber;

import static com.codeborne.selenide.WebDriverRunner.hasWebDriverStarted;

import com.codeborne.selenide.WebDriverRunner;
import com.github.lexshcherbinin.kleekai.ui.BaseMethods;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;

/**
 * Снятие скриншота текущей страницы в случае падения теста, очистка всех cookies и закрытие браузера после каждого теста
 */
public final class TakeScreenshotAndCloseWebDriverHook {

  @After
  public void takeScreenshotAndCloseWebDriver(Scenario scenario) {
    if (scenario.isFailed() && WebDriverRunner.hasWebDriverStarted()) {
      BaseMethods.takeScreenshot();
    }

    if (hasWebDriverStarted()) {
      WebDriverRunner.getWebDriver().manage().deleteAllCookies();
      WebDriverRunner.closeWebDriver();
    }
  }

}