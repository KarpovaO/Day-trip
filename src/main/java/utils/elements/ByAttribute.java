package utils.elements;

import org.openqa.selenium.By;

/**
Класс для упрощения работы с By
*/
public class ByAttribute {

    /**
    Возвращает By по совпадению атрибута @class и тексту элемента
    */
    public static By byClassAndTextContaining(String element, String className, String text) {
        return By.xpath(String.format(".//%s[contains(@class,'%s') and contains(text(), '%s')]", element, className, text));
    }

    /**
    Возвращает By по совпадению атрибута @class и тексту элемента
    */
    public static By byClassAndTextContaining(String className, String text) {
        return By.xpath(String.format(".//*[contains(@class,'%s') and contains(text(), '%s')]", className, text));
    }

    /**
    Возвращает By по совпадению атрибута @class
    */
    public static By byClassContaining(String element, String className) {
        return By.xpath(String.format(".//%s[contains(@class,'%s')]", element, className));
    }
    /*
    Возвращает By по совпадению атрибута @class
    */
    public static By byClassContaining(String className) {
        return By.xpath(String.format(".//*[contains(@class,'%s')]", className));
    }
}
