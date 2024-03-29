package com.github.lexshcherbinin.kleekai.ui.steps;

import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static com.github.lexshcherbinin.kleekai.ui.KleeKaiPage.TIME_FOR_DOWNLOAD_FILE;

import com.codeborne.selenide.Selenide;
import com.github.lexshcherbinin.kleekai.ui.BaseMethods;
import com.github.lexshcherbinin.kleekai.ui.KleeKaiPage;
import io.qameta.allure.Step;
import java.io.File;
import java.util.List;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

/**
 * Шаги для взаимодействия с вэб-страницей.
 */
public interface WebPageInteractionSteps<T extends KleeKaiPage<T>> {

  @Step("Выполнен переход по ссылке \"{url}\"")
  default T goToUrl(String url) {
    Selenide.open(url);
    return (T) this;
  }

  @Step("Выполнено обновление текущей страницы")
  default T refreshPage() {
    Selenide.refresh();
    return (T) this;
  }

  @Step("Выполнен переход в конец страницы")
  default T scrollDown() {
    Actions actions = new Actions(getWebDriver());
    actions.keyDown(Keys.CONTROL).sendKeys(Keys.END).build().perform();
    actions.keyUp(Keys.CONTROL).perform();
    return (T) this;
  }

  @Step("Страница прокручена до элемента \"{elementName}\"")
  default T scrollPageToElement(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).scrollTo();
    return (T) this;
  }

  @Step("Выполнено переключение на вкладку с заголовком \"{title}\"")
  default T switchToTheTabWithTitle(String title) {
    Selenide.switchTo().window(title);
    return (T) this;
  }

  @Step("Выполнена загрузка файла \"{fileName}\"")
  default T clickUploadFile(String buttonName, String fileName) {
    BaseMethods.attachExelFile(fileName, fileName);
    ((KleeKaiPage<?>) this).getElement(buttonName).uploadFile(new File(fileName));
    return (T) this;
  }

  @Step("Выполнена загрузка файлов \"{fileNameList}\"")
  default T clickUploadFileList(String buttonName, List<String> fileNameList) {
    for (String fileName : fileNameList) {
      clickUploadFile(buttonName, fileName);
    }
    return (T) this;
  }

  @Step("Выполнено скачивание файла")
  default T clickDownloadFile(String elementName) {
    ((KleeKaiPage<?>) this).getElement(elementName).click();
    Selenide.sleep(TIME_FOR_DOWNLOAD_FILE);
    return (T) this;
  }

}