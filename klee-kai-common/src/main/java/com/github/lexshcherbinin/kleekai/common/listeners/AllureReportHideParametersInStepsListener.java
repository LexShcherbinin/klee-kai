package com.github.lexshcherbinin.kleekai.common.listeners;

import com.github.lexshcherbinin.kleekai.common.Environment;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;

/**
 * Скрытие параметров в шагах отчёта.
 */
public final class AllureReportHideParametersInStepsListener implements IInvokedMethodListener {

  @Override
  public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
    Environment.hideParametersInSteps();
  }

}