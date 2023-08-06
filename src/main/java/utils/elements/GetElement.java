package utils.elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.$x;

//Класс для упрощения работы по поиску элементов в DOM

public class GetElement {

    //Получить элемент кнопки по его названию

    public static SelenideElement buttonByText(String text) {
        return buttonByText(null, text);
    }

    public static SelenideElement buttonByText(SelenideElement prefixElement, String text) {
        String xPath = String.format(".//button[contains(., '%s')]", text);
        return prefixElement != null ? prefixElement.$x(xPath) : $x(xPath);
    }



}
