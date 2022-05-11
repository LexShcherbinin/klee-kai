package com.github.lexshcherbinin.kleekai.ui.listeners.junit;

import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Очистка всех cookies и закрытие браузера после прогона всех тестов CloseWebDriverAfterClassListener нужен только в тех тестах,
 * где до нужной страницы надо долго добираться и только потом там прогонять несколько тестов, (чтобы после первого упавшего не
 * проходить до этой страницы заново)
 */
public final class CloseWebDriverExtension implements AfterAllCallback {

  @Override
  public void afterAll(ExtensionContext context) {
    if (WebDriverRunner.hasWebDriverStarted()) {
      WebDriverRunner.getWebDriver().manage().deleteAllCookies();
      WebDriverRunner.closeWebDriver();
    }
  }

}