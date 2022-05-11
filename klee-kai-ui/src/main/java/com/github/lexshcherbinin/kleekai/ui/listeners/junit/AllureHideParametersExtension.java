package com.github.lexshcherbinin.kleekai.ui.listeners.junit;

import com.github.lexshcherbinin.kleekai.ui.BaseMethods;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Скрытие параметров в шагах
 */
public final class AllureHideParametersExtension implements AfterEachCallback {

  @Override
  public void afterEach(ExtensionContext context) {
    BaseMethods.hideParametersInSteps();
  }

}