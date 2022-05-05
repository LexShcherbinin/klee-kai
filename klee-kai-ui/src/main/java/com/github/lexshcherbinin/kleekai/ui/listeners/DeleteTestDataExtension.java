package com.github.lexshcherbinin.kleekai.ui.listeners;

import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Создаёт директорию для тестовых файлов перед прохождением и удаляет её после прохождения тестов. Директория по умолчанию:
 * target/generated-sources/test-data/
 */
public final class DeleteTestDataExtension implements BeforeAllCallback, AfterAllCallback {

  @Override
  public void beforeAll(ExtensionContext context) {
    try {
      Files.createDirectory(Paths.get(getTestDataPath()));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void afterAll(ExtensionContext context) {
    try {
      System.gc();
      FileUtils.deleteDirectory(new File(getTestDataPath()));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Ищет в application.conf дирректорию, в которой будут создаваться тестовые файлы
   *
   * @return - возвращает значение temp_test_data_path из application.conf. При его отсутствии возвращает значение
   * target/generated-sources/test-data/
   */
  private String getTestDataPath() {
    String defaultPath = "target/generated-sources/test-data/";
    String tempTestDataPath;

    try {
      tempTestDataPath = ConfigFactory.load().getString("temp_test_data_path");

    } catch (ConfigException e) {
      tempTestDataPath = defaultPath;
    }

    if (tempTestDataPath.equals("")) {
      return defaultPath;
    }

    return tempTestDataPath;
  }


}
