package com.github.lexshcherbinin.kleekai.ui.listeners.testng;

import com.github.lexshcherbinin.kleekai.ui.BaseMethods;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;

/**
 * Скрытие параметров в шагах
 */
public final class AllureHideParametersAfterMethodListener implements IInvokedMethodListener {

  @Override
  public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
    BaseMethods.hideParametersInSteps();
  }

}