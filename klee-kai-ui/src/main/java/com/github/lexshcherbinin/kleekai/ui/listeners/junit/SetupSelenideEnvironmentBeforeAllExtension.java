package com.github.lexshcherbinin.kleekai.ui.listeners.junit;

import com.github.lexshcherbinin.kleekai.ui.BaseMethods;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Задание настроек окружения
 */
public final class SetupSelenideEnvironmentBeforeAllExtension implements BeforeAllCallback {

  @Override
  public void beforeAll(ExtensionContext context) {
    BaseMethods.setupSelenideEnvironment();
  }

}