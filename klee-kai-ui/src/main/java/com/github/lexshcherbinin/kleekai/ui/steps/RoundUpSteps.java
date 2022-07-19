package com.github.lexshcherbinin.kleekai.ui.steps;

import com.codeborne.selenide.Selenide;
import com.github.lexshcherbinin.kleekai.ui.BaseMethods;
import com.github.lexshcherbinin.kleekai.ui.KleeKaiPage;
import io.qameta.allure.Step;
import java.awt.AWTException;
import java.awt.Robot;
import org.openqa.selenium.Keys;

/**
 * Шаги для тестирования взаимодействия с внешним окружением.
 */
public interface RoundUpSteps<T extends KleeKaiPage<T>> {

  @Step("Выполнено ожидание в течение \"{seconds}\" секунд")
  default T waitForSeconds(long seconds) {
    Selenide.sleep(seconds * 1000);
    return (T) this;
  }

  @Step("Выполнено нажатие на клавиатуре \"{buttonName}\"")
  default T pushButtonOnKeyboard(String buttonName) {
    Keys key = Keys.valueOf(buttonName.toUpperCase());
    Selenide.switchTo().activeElement().sendKeys(key);
    return (T) this;
  }

  @Step("Выполнено нажатие на клавиатуре \"{buttonName}\"")
  default T pushButtonOnKeyboard(int key) {
    try {
      new Robot().keyPress(key);

    } catch (AWTException e) {
      e.printStackTrace();
    }
    return (T) this;
  }

  @Step("Выполнен js-скрипт \"{scriptName}\"")
  default T executeJsScript(String scriptName) {
    Selenide.executeJavaScript(scriptName);
    return (T) this;
  }

  @Step("Снят скриншот текущей страницы")
  default T takeScreenshot() {
    BaseMethods.takeScreenshot();
    return (T) this;
  }

}