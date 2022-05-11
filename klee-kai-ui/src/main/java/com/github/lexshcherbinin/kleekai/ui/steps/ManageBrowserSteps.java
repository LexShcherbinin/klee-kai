package com.github.lexshcherbinin.kleekai.ui.steps;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

import com.codeborne.selenide.Selenide;
import com.github.lexshcherbinin.kleekai.ui.KleeKaiPage;
import io.qameta.allure.Step;
import java.util.Set;
import org.openqa.selenium.Dimension;

/**
 * Шаги для взаимодействия с браузером.
 */
public interface ManageBrowserSteps<T extends KleeKaiPage<T>> {

  @Step("Выполнена очистка cookies браузера")
  default T deleteCookies() {
    Selenide.clearBrowserCookies();
    return (T) this;
  }

  @Step("Выполнено переключение на следующую вкладку")
  default T switchToTheNextTab() {
    String nextWindowHandle = nextWindowHandle();
    getWebDriver().switchTo().window(nextWindowHandle);
    return (T) this;
  }

  @Step("Выполнено закрытие текущей вкладки")
  default T closeCurrentTab() {
    getWebDriver().close();
    return (T) this;
  }

  @Step("Установлено разрешение окна браузера {width}x{height}")
  default T setBrowserWindowSize(int width, int height) {
    getWebDriver().manage().window().setSize(new Dimension(width, height));
    return (T) this;
  }

  @Step("Окно браузера развернуто на весь экран")
  default T expandWindowToFullScreen() {
    getWebDriver().manage().window().maximize();
    return (T) this;
  }

  //TODO: Добавить описание данному методу
  private String nextWindowHandle() {
    String currentWindowHandle = getWebDriver().getWindowHandle();
    Set<String> windowHandles = getWebDriver().getWindowHandles();
    windowHandles.remove(currentWindowHandle);
    return windowHandles.iterator().next();
  }

}