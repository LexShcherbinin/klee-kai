package com.github.lexshcherbinin.kleekai.ui.listeners;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

/**
 * Удаление всех скачанных во время прогона теста файлов
 */
public final class DeleteDownloadFileExtension implements AfterEachCallback {

  @Override
  public void afterEach(ExtensionContext context) {
    try {
      FileUtils.deleteDirectory(new File(System.getProperty("selenide.downloadsFolder")));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
