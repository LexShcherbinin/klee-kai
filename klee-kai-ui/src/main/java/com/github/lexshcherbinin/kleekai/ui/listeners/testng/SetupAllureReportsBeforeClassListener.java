package com.github.lexshcherbinin.kleekai.ui.listeners.testng;

import com.github.lexshcherbinin.kleekai.ui.BaseMethods;
import org.testng.IClassListener;
import org.testng.ITestClass;

/**
 * Установка AllureSelenideLogger
 */
public final class SetupAllureReportsBeforeClassListener implements IClassListener {

  @Override
  public void onBeforeClass(ITestClass testClass) {
    BaseMethods.setupAllureReports();
  }

}