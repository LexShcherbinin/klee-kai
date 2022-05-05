package com.github.lexshcherbinin.kleekai.ui.listeners;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.model.Status.FAILED;

import com.codeborne.selenide.WebDriverRunner;
import com.github.lexshcherbinin.kleekai.ui.BaseMethods;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Снятие скриншота текущей страницы в случае падения теста, очистка всех cookies и закрытие браузера после каждого теста
 */
public final class TakeScreenshotAndCloseWebDriverExtension implements AfterEachCallback {

  @Override
  public void afterEach(ExtensionContext context) throws Exception {
    if (WebDriverRunner.hasWebDriverStarted()) {
      Allure.getLifecycle().updateStep(
          s -> {
            if (s.getStatus().equals(FAILED)) {
              takeScreenshot();
            }
          }
      );

      closeWebDriver();
    }
  }

  //    @Step("Очистка всех cookies, закрытие браузера")
  public void closeWebDriver() {
    getWebDriver().manage().deleteAllCookies();
    WebDriverRunner.closeWebDriver();
  }

  @Step("Снятие скриншота текущей страницы")
  public void takeScreenshot() {
    BaseMethods.takeScreenshot();
  }
}
