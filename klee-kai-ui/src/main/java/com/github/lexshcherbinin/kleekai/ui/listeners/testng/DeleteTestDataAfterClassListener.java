package com.github.lexshcherbinin.kleekai.ui.listeners.testng;

import com.github.lexshcherbinin.kleekai.common.FileCreator;
import com.github.lexshcherbinin.kleekai.ui.Environment;
import org.testng.IClassListener;
import org.testng.ITestClass;

/**
 * Создаёт директорию для тестовых файлов перед прохождением и удаляет её после прохождения тестов. Директория по умолчанию:
 * target/generated-sources/test-data/
 */
public final class DeleteTestDataAfterClassListener implements IClassListener {

  private static final String PATH = Environment.getTestDataPath();

  @Override
  public void onBeforeClass(ITestClass testClass) {
    FileCreator.createDirectory(PATH);
  }

  @Override
  public void onAfterClass(ITestClass testClass) {
    FileCreator.deleteDirectory(PATH);
  }

}