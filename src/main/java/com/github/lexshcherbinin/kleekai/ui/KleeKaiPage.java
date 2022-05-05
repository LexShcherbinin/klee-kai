package com.github.lexshcherbinin.kleekai.ui;

import static com.github.lexshcherbinin.kleekai.ui.BaseMethods.getConfigValueOrDefault;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.github.lexshcherbinin.kleekai.ui.steps.ManageBrowserSteps;
import com.github.lexshcherbinin.kleekai.ui.steps.RoundUpSteps;
import com.github.lexshcherbinin.kleekai.ui.steps.WebPageInteractionSteps;
import com.github.lexshcherbinin.kleekai.ui.annotations.Block;
import com.github.lexshcherbinin.kleekai.ui.annotations.Hidden;
import com.github.lexshcherbinin.kleekai.ui.annotations.Name;
import com.github.lexshcherbinin.kleekai.ui.annotations.Required;
import io.qameta.allure.Allure;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Главный пейдж-класс, от которого наследуются все остальные пейджи
 */
public class KleeKaiPage<D extends KleeKaiPage<D>> implements WebPageInteractionSteps<D>, ManageBrowserSteps<D>, RoundUpSteps<D> {

  public static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds((long) getConfigValueOrDefault("default_timeout", 30L));

  public static final long TIME_FOR_DOWNLOAD_FILE = (long) getConfigValueOrDefault("time_for_download_file", 10000L);

  /**
   * Список всех элементов страницы, обозначенных аннотацией "Name"
   */
  private Map<String, Object> namedElements;

  /**
   * Список всех элементов страницы, обозначенных аннотацией "Name" и "Required"
   */
  private Map<String, Object> requiredElements;

  /**
   * Список всех элементов страницы, обозначенных аннотацией "Name"и "Hidden"
   */
  private Map<String, Object> hiddenElements;

  /**
   * Список всех блоков страницы, обозначенных аннотацией "Block"
   */
  private List<KleeKaiPage> blocks;

  /**
   * Единая точка входа для всех тестов: BasePage.getInstance().goToUrl(url)...
   */
  public static KleeKaiPage<?> getInstance() {
    return new KleeKaiPage<>();
  }

  /**
   * Получение экземпляра страницы
   */
  public final <T extends KleeKaiPage<T>> T getPage(Class<T> page) {
    Allure.step(String.format("Выполнена загрузка страницы '%s'", getPageName(page)));
    T instance = Selenide.page(page);
    instance.initializePageElements(page, instance);
    return instance;
  }

  /**
   * Получение экземпляра блока страницы
   */
  public final <T extends KleeKaiPage<T>> T getBlock(Class<T> page) {
    T instance = Selenide.page(page);
    instance.initializePageElements(page, instance);
    return instance;
  }

  /**
   * Получение имени страницы. Если над страницей есть аннотация @Name, будет использовано её значение, если нет - имя класса
   */
  private <T> String getPageName(Class<T> page) {
    try {
      return page.getDeclaredAnnotation(Name.class).value();

    } catch (NullPointerException e) {
      return page.getSimpleName();
    }
  }

  /**
   * Получение элемента страницы по имени (@Name("Имя элемента"))
   */
  public SelenideElement getElement(String elementName) {
    SelenideElement element = getElementInBlock(elementName);

    if (element != null) {
      return element;

    } else {
      throw new IllegalArgumentException(
          String.format("Элемент '%s' не описан на странице '%s'", elementName, this.getClass().getName()));
    }
  }

  public SelenideElement getElementInBlock(String elementName) {
    if (namedElements.get(elementName) != null) {
      return (SelenideElement) namedElements.get(elementName);

    } else {
      for (KleeKaiPage<?> block : blocks) {
        SelenideElement elementInBlock = block.getElementInBlock(elementName);

        if (elementInBlock != null) {
          return elementInBlock;
        }
      }
    }

    return null;
  }

  /**
   * Получение списка элементов страницы по имени (@Name("Имя элемента"))
   */
  //TODO: Надо доработать метод
  public List<SelenideElement> getElementList(String elementName) {
    return ((List<SelenideElement>) namedElements.get(elementName));
  }

  /**
   * Инициализация элементов страницы. Проверяет наличие обязательных элементов и отсутствие тех, что быть на странице не должны
   */
  <T extends KleeKaiPage<T>> void initializePageElements(Class<T> page, KleeKaiPage<? extends KleeKaiPage<?>> instance) {
    instance.namedElements = readNamedElements(page);
    instance.requiredElements = readRequiredElements(page);
    instance.hiddenElements = readHiddenElements(page);
    instance.blocks = readBlocks(page);

    for (String key : requiredElements.keySet()) {
      BaseMethods.castToSelenideElement(requiredElements.get(key)).should(Condition.appear, DEFAULT_TIMEOUT);
    }

    for (String key : hiddenElements.keySet()) {
      BaseMethods.castToSelenideElement(hiddenElements.get(key)).should(Condition.hidden, DEFAULT_TIMEOUT);
    }
  }

  /**
   * Поиск элементов страницы, обозначенных аннотацией "Required"
   */
  private <T> Map<String, Object> readRequiredElements(Class<T> page) {
    return Arrays.stream(page.getDeclaredFields())
        .filter(field -> field.getDeclaredAnnotation(Name.class) != null && field.getDeclaredAnnotation(Required.class) != null)
        .collect(toMap(
            field -> field.getDeclaredAnnotation(Name.class).value(),
            field -> getFieldValue(page, field))
        );
  }

  /**
   * Поиск элементов страницы, обозначенных аннотацией "Hidden"
   */
  private <T> Map<String, Object> readHiddenElements(Class<T> page) {
    return Arrays.stream(page.getDeclaredFields())
        .filter(field -> field.getDeclaredAnnotation(Name.class) != null && field.getDeclaredAnnotation(Hidden.class) != null)
        .collect(toMap(
            field -> field.getDeclaredAnnotation(Name.class).value(),
            field -> getFieldValue(page, field))
        );
  }

  /**
   * Поиск элементов страницы, обозначенных аннотацией "Name"
   */
  private <T> Map<String, Object> readNamedElements(Class<T> page) {
    checkNamedAnnotations(page);
    return Arrays.stream(page.getDeclaredFields())
        .filter(field -> field.getDeclaredAnnotation(Name.class) != null)
        .collect(toMap(
            field -> field.getDeclaredAnnotation(Name.class).value(),
            field -> getFieldValue(page, field))
        );
  }

  /**
   * Поиск блоков страницы, обозначенных аннотацией "Block"
   */
  private <T> List<KleeKaiPage> readBlocks(Class<T> page) {
    return Arrays.stream(page.getDeclaredFields())
        .filter(field -> field.getDeclaredAnnotation(Block.class) != null)
        .map(field -> ((KleeKaiPage<?>) getFieldValue(page, field)))
        .collect(toList());
  }

  /**
   * Проверка наличия нескольких полей с одинаковым именем (значением аннотации "Name")
   */
  private <T> void checkNamedAnnotations(Class<T> page) {
    List<String> list = Arrays.stream(page.getDeclaredFields())
        .filter(field -> field.getDeclaredAnnotation(Name.class) != null)
        .map(field -> field.getDeclaredAnnotation(Name.class).value())
        .collect(toList());

    if (list.size() != new HashSet<>(list).size()) {
      throw new IllegalStateException("Найдено несколько аннотаций @Name с одинаковым значением в классе " + page.getName());
    }
  }

  /**
   * Получение значения поля
   */
  private <T> Object getFieldValue(Class<T> page, Field field) {
    field.setAccessible(true);

    try {
      Constructor<T> constructor = page.getDeclaredConstructor();
      constructor.setAccessible(true);

      return field.get(constructor.newInstance());

    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      e.printStackTrace();
      throw new IllegalArgumentException("Не удалось получить значение поля " + field);
    }
  }
}
