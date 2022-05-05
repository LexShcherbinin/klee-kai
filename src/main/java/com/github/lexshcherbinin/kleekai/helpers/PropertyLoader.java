package com.github.lexshcherbinin.kleekai.helpers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Класс для работы с properties-файлами.
 */
public class PropertyLoader {

  /**
   * Читает properties-файл.
   *
   * @param fileName - имя файла (полный путь)
   * @return - возвращает все property из указанного файла
   */
  public static Properties loadPropertiesFile(String fileName) {
    Properties properties = new Properties();

    try {
      properties.load(new FileInputStream(fileName));

    } catch (FileNotFoundException e) {
      throw new RuntimeException("Не был найден файл " + fileName);

    } catch (IOException e) {
      throw new RuntimeException("Не удалось прочитать файл " + fileName);
    }

    return properties;
  }

}
