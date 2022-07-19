package com.github.lexshcherbinin.kleekai.common.listeners;

import com.github.lexshcherbinin.kleekai.common.AllureReport;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import org.testng.IClassListener;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestContext;
import org.testng.ITestResult;

/**
 * Синхронизация вкладок Suites и Behaviors в отчёте.
 */
public final class AllureReportListener implements IClassListener, IInvokedMethodListener {

  @Override
  public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
    Map<Class<? extends Annotation>, Annotation> annotations = Arrays.stream(
            method
                .getTestMethod()
                .getConstructorOrMethod()
                .getDeclaringClass()
                .getDeclaredAnnotations()
        )
        .collect(Collectors.toMap(
            Annotation::annotationType,
            annotation -> annotation
        ));

    AllureReport allureReport = AllureReport.getInstance().clearLabels();

    if (annotations.get(Epic.class) != null) {
      String epic = ((Epic) annotations.get(Epic.class)).value();

      allureReport
          .setParentSuite(epic)
          .setEpic(epic);
    }

    if (annotations.get(Feature.class) != null) {
      String feature = ((Feature) annotations.get(Feature.class)).value();

      allureReport
          .setSuite(feature)
          .setFeature(feature);
    }

    if (annotations.get(Story.class) != null) {
      String story = ((Story) annotations.get(Story.class)).value();

      allureReport
          .setSubSuite(story)
          .setStory(story);
    }
  }

}