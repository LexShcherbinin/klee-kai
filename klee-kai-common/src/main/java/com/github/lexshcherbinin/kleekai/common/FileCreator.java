package com.github.lexshcherbinin.kleekai.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;

public class FileCreator {

  /**
   * Создать директорию
   *
   * @param path - директория
   */
  public static void createDirectory(String path) {
    try {
      Files.createDirectory(Paths.get(path));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Удалить директорию
   *
   * @param path - директория
   */
  public static void deleteDirectory(String path) {
    try {
      System.gc();
      FileUtils.deleteDirectory(new File(path));

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
