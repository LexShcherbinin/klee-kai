package com.github.lexshcherbinin.kleekai.ui.listeners.testng;

import com.codeborne.selenide.WebDriverRunner;
import com.github.lexshcherbinin.kleekai.ui.BaseMethods;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;

/**
 * Снятие скриншота текущей страницы в случае падения теста
 */
public final class TakeScreenshotAfterMethodListener implements IInvokedMethodListener {

  @Override
  public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
    if (!testResult.isSuccess() && WebDriverRunner.hasWebDriverStarted()) {
      BaseMethods.takeScreenshot();
    }
  }

}