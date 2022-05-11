package com.github.lexshcherbinin.kleekai.ui.listeners.testng;

import com.github.lexshcherbinin.kleekai.ui.Environment;
import org.testng.IClassListener;
import org.testng.ITestClass;

/**
 * Задание настроек окружения
 */
public final class SetupSelenideEnvironmentBeforeClassListener implements IClassListener {

  @Override
  public void onBeforeClass(ITestClass testClass) {
    Environment.setupSelenideEnvironment();
  }

}