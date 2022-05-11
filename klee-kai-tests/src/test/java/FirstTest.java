import com.github.lexshcherbinin.kleekai.ui.KleeKaiPage;
import com.github.lexshcherbinin.kleekai.ui.listeners.junit.AllureHideParametersExtension;
import com.github.lexshcherbinin.kleekai.ui.listeners.junit.SetupSelenideEnvironmentExtension;
import com.github.lexshcherbinin.kleekai.ui.listeners.junit.TakeScreenshotAndCloseWebDriverExtension;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({
    SetupSelenideEnvironmentExtension.class,
    TakeScreenshotAndCloseWebDriverExtension.class,
    AllureHideParametersExtension.class
})
public class FirstTest {

  @Test
  public void first() {

    KleeKaiPage.getInstance()
        .goToUrl("https://yandex.ru")
        .waitForSeconds(2);
  }

}
