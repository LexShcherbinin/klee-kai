package com.github.lexshcherbinin.kleekai.ui.listeners.junit;

import com.github.lexshcherbinin.kleekai.ui.BaseMethods;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Создаёт директорию для тестовых файлов перед прохождением и удаляет её после прохождения тестов. Директория по умолчанию:
 * target/generated-sources/test-data/
 */
public final class DeleteTestDataExtension implements BeforeAllCallback, AfterAllCallback {

  private static final String PATH = BaseMethods.getTestDataPath();

  @Override
  public void beforeAll(ExtensionContext context) {
    BaseMethods.createDirectory(PATH);
  }

  @Override
  public void afterAll(ExtensionContext context) {
    BaseMethods.deleteDirectory(PATH);
  }

}