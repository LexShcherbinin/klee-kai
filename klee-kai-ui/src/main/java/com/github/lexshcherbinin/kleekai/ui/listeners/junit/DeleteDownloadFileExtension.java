package com.github.lexshcherbinin.kleekai.ui.listeners.junit;

import com.github.lexshcherbinin.kleekai.common.FileCreator;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Удаление всех скачанных во время прогона теста файлов
 */
public final class DeleteDownloadFileExtension implements AfterEachCallback {

  @Override
  public void afterEach(ExtensionContext context) {
    FileCreator.deleteDirectory(System.getProperty("selenide.downloadsFolder"));
  }

}