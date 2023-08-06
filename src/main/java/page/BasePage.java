package page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import elements.PaymentForm;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static utils.elements.ByAttribute.byClassContaining;
import static utils.elements.GetElement.buttonByText;

public class BasePage {

    // Заголовок страницы 'Путешествие дня'
    private SelenideElement mainHeader() {
        return $x("//h2");
    }

    // Заголовок названия города
    private SelenideElement cityName() {
        return cardPreviewArea().$(byClassContaining("h3", "Order_cardHeading"));
    }

    // Область информации о туре
    private SelenideElement cardPreviewArea() {
        return $(byClassContaining("Order_cardPreview"));
    }

    //Элементы информации о туре
    private ElementsCollection descriptionElements() {
        return cardPreviewArea().$$(byClassContaining("li", "list__item"));
    }

    //Короткое описание
    private SelenideElement shortDescriptionElement() {
        return descriptionElements().get(0);
    }

    //Информация о бонусах (милях)
    private SelenideElement milesOnCardElement() {
        return descriptionElements().get(1);
    }

    //Информация о кэшбеке
    private SelenideElement cashBackElement() {
        return descriptionElements().get(2);
    }

    //Информация о цене
    private SelenideElement priceElement() {
        return descriptionElements().get(3);
    }

    // Кнопка Купить
    private SelenideElement buyButton() {
        return buttonByText("Купить");
    }

    //Кнопка Купить в кредит
    private SelenideElement buyInCreditButton() {
        return buttonByText("Купить в кредит");
    }

    @Step("Нажать кнопку Купить")
    public PaymentForm buyButtonClick() {
        buyButton().shouldBe(visible, enabled).click();
        return new PaymentForm("Оплата по карте");
    }

    @Step("Нажать кнопку Купить в Кредит")
    public PaymentForm buyingCreditButtonClick() {
        buyInCreditButton().shouldBe(visible, enabled).click();
        return new PaymentForm("Кредит по данным карты");
    }

    @Step("Проверить, что заголовок страницы видно и он соответствует тексту = {value}")
    public BasePage checkHeaderEqual(String value) {
        mainHeader().shouldBe(visible, text(value));
        return this;
    }

    @Step("Проверить, что заголовок названия города видно и он соответствует тексту = {value}")
    public BasePage checkHeaderCityEqual(String value) {
        cityName().shouldBe(visible).shouldHave(text(value));
        return this;
    }

    @Step("Проверить, что заголовок короткого описания видно и он соответствует тексту = {description}")
    public BasePage checkDescriptionEqual(String description) {
        shortDescriptionElement().shouldBe(visible, text(description));
        return this;
    }

    @Step("Проверить, что заголовок с бонусными милями видно и он соответствует тексту = {miles}")
    public BasePage checkMilesOnCardEqual(String miles) {
        milesOnCardElement().shouldBe(visible, text(miles));
        return this;
    }

    @Step("Проверить, что заголовок с кэшбеком видно и он соответствует тексту = {cashBack}")
    public BasePage checkCashBackEqual(String cashBack) {
        cashBackElement().shouldBe(visible, text(cashBack));
        return this;
    }

    @Step("Проверить, что заголовок с ценой видно и он соответствует тексту = {price}")
    public BasePage checkPriceEqual(String price) {
        priceElement().shouldBe(visible, text(price));
        return this;
    }

    @Step("Получить стоимость выбранной путевки")
    public Integer getTripPrice(){
        return Integer.parseInt(priceElement().text().replaceAll("\\D", ""));
    }
}
