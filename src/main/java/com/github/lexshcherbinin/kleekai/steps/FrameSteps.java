package com.github.lexshcherbinin.kleekai.steps;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.github.lexshcherbinin.kleekai.ui.BasePage;
import io.qameta.allure.Step;

/**
 * Шаги для взаимодействия с фреймами на странице
 */
public interface FrameSteps<T extends BasePage<T>> {

  @Step("Выполнен переход на фрейм '{elementName}'")
  default T switchToFrame(String elementName) {
    SelenideElement frame = ((BasePage<?>) this).getElement(elementName);
    Selenide.switchTo().frame(frame);
    return (T) this;
  }

  @Step("Выполнен переход на фрейм номер '{frameNumber}'")
  default T switchToFrame(int frameNumber) {
    Selenide.switchTo().frame(frameNumber);
    return (T) this;
  }

  @Step("Выполнен переход на родительский фрейм")
  default T switchToParentFrame() {
    Selenide.switchTo().parentFrame();
    return (T) this;
  }

  @Step("Выполнен переход на дефолтный фрейм")
  default T switchToDefaultContent() {
    Selenide.switchTo().defaultContent();
    return (T) this;
  }
}