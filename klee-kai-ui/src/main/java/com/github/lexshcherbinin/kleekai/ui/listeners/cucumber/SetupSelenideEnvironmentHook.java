package com.github.lexshcherbinin.kleekai.ui.listeners.cucumber;

import com.github.lexshcherbinin.kleekai.ui.Environment;
import io.cucumber.java.Before;

/**
 * Задание настроек окружения
 */
public final class SetupSelenideEnvironmentHook {

  @Before
  public void setupSelenideEnvironment() {
    Environment.setupSelenideEnvironment();
  }

}