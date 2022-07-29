package com.github.lexshcherbinin.kleekai.ui.steps;

import com.codeborne.selenide.WebDriverRunner;
import com.github.lexshcherbinin.kleekai.ui.KleeKaiPage;
import io.qameta.allure.Step;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v103.network.Network;
import org.openqa.selenium.devtools.v103.network.model.Request;

/**
 * Шаги для перехвата API-запросов
 */
public interface RequestCatcherSteps<T extends KleeKaiPage<T>> {

  /**
   * Список API-запросов, пойманных перехватчиком
   */
  List<Request> requestList = new ArrayList<>();

  @Step("Включение перехватчика API-запросов")
  default T enableRequestCatcher() {
    ChromeDriver driver = (ChromeDriver) WebDriverRunner.getWebDriver();
    DevTools devTools = driver.getDevTools();
    devTools.createSession();

    devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

    devTools.addListener(
        Network.requestWillBeSent(),
        entry -> requestList.add(entry.getRequest())
    );

    return (T) this;
  }

  @Step("Отключение перехватчика API-запросов")
  default T disableRequestCatcher() {
    ChromeDriver driver = (ChromeDriver) WebDriverRunner.getWebDriver();
    DevTools devTools = driver.getDevTools();
    devTools.send(Network.disable());
    return (T) this;
  }

  @Step("Очистка списка перехваченных API-запросов")
  default T clearRequestList() {
    requestList.clear();
    return (T) this;
  }

}