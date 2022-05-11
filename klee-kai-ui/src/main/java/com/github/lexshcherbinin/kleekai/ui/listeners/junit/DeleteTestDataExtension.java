package com.github.lexshcherbinin.kleekai.ui.listeners.junit;

import com.github.lexshcherbinin.kleekai.common.FileCreator;
import com.github.lexshcherbinin.kleekai.ui.Environment;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Создаёт директорию для тестовых файлов перед прохождением и удаляет её после прохождения тестов. Директория по умолчанию:
 * target/generated-sources/test-data/
 */
public final class DeleteTestDataExtension implements BeforeAllCallback, AfterAllCallback {

  private static final String PATH = Environment.getTestDataPath();

  @Override
  public void beforeAll(ExtensionContext context) {
    FileCreator.createDirectory(PATH);
  }

  @Override
  public void afterAll(ExtensionContext context) {
    FileCreator.deleteDirectory(PATH);
  }

}