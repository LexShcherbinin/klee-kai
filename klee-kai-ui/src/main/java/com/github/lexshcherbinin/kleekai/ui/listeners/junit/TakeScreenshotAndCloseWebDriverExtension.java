package com.github.lexshcherbinin.kleekai.ui.listeners.junit;

import static io.qameta.allure.model.Status.FAILED;

import com.codeborne.selenide.WebDriverRunner;
import com.github.lexshcherbinin.kleekai.ui.BaseMethods;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Снятие скриншота текущей страницы в случае падения теста, очистка всех cookies и закрытие браузера после каждого теста
 */
public final class TakeScreenshotAndCloseWebDriverExtension implements AfterEachCallback {

  @Override
  public void afterEach(ExtensionContext context) {
    if (WebDriverRunner.hasWebDriverStarted()) {
      Allure.getLifecycle().updateStep(
          s -> {
            if (s.getStatus().equals(FAILED)) {
              BaseMethods.takeScreenshot();
            }
          }
      );

      BaseMethods.deleteAllCookies();
      BaseMethods.closeWebDriver();
    }
  }

}