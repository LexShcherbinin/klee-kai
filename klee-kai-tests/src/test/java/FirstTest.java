import com.github.lexshcherbinin.kleekai.ui.KleeKaiPage;
import com.github.lexshcherbinin.kleekai.ui.listeners.AllureHideParametersExtension;
import com.github.lexshcherbinin.kleekai.ui.listeners.SetupSelenideEnvironmentBeforeAllExtension;
import com.github.lexshcherbinin.kleekai.ui.listeners.TakeScreenshotAndCloseWebDriverExtension;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({
    SetupSelenideEnvironmentBeforeAllExtension.class,
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
