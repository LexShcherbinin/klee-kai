package com.github.lexshcherbinin.kleekai.ui.listeners.junit;

import com.github.lexshcherbinin.kleekai.ui.Environment;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Установка AllureSelenideLogger
 */
public final class SetupAllureReportsExtension implements BeforeAllCallback {

  @Override
  public void beforeAll(ExtensionContext context) {
    Environment.setupAllureReports();
  }

}