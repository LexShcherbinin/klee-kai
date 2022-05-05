package com.github.lexshcherbinin.kleekai.ui.listeners;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Установка AllureSelenideLogger
 */
public final class SetupAllureReportsExtension implements BeforeAllCallback {

  @Override
  public void beforeAll(ExtensionContext context) {
    setupAllureReports();
  }

  @Step("Установка AllureSelenideLogger")
  public void setupAllureReports() {
    SelenideLogger.addListener(
        "AllureSelenideLogger",
        new AllureSelenide().screenshots(true).savePageSource(true).includeSelenideSteps(false)
    );
  }
}
