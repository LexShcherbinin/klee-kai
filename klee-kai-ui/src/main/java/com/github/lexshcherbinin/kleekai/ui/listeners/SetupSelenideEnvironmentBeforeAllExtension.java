package com.github.lexshcherbinin.kleekai.ui.listeners;

import com.github.lexshcherbinin.kleekai.common.PropertyLoader;
import java.util.Properties;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Задание настроек окружения
 */
public final class SetupSelenideEnvironmentBeforeAllExtension implements BeforeAllCallback {

  @Override
  public void beforeAll(ExtensionContext context) {
    setupSelenideProperties();
  }

  //    @Step("Задание настроек окружения")
  public void setupSelenideProperties() {
    String selenidePropertiesPath = "src/main/resources/selenide.properties";
    Properties properties = PropertyLoader.loadPropertiesFile(selenidePropertiesPath);

    for (Object property : properties.keySet()) {
      String key = property.toString();
      String value = System.getProperty(key);

      if (value == null) {
        value = properties.getProperty(key);
      }

      System.setProperty(key, value);
    }
  }
}
