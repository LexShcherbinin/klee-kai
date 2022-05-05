package com.github.lexshcherbinin.kleekai.ui.listeners;

import static io.qameta.allure.model.Status.FAILED;

import com.codeborne.selenide.WebDriverRunner;
import com.github.lexshcherbinin.kleekai.ui.BaseMethods;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Снятие скриншота текущей страницы в случае падения теста
 */
public final class TakeScreenshotExtension implements AfterEachCallback {

  @Override
  public void afterEach(ExtensionContext context) {
    if (WebDriverRunner.hasWebDriverStarted()) {
      Allure.getLifecycle().updateStep(
          s -> {
            if (s.getStatus().equals(FAILED)) {
              takeScreenshot();
            }
          }
      );
    }
  }

  @Step("Снятие скриншота текущей страницы")
  public void takeScreenshot() {
    BaseMethods.takeScreenshot();
  }
}
