package elements;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import page.BasePage;
import utils.elements.GetElement;

import java.util.function.Function;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static utils.elements.ByAttribute.byClassAndTextContaining;

/**
 * Класс для работы с областью ввода данных оплаты
 */

public class PaymentForm {

    private final String paymentHeader;

    public PaymentForm(String paymentHeader) {
        this.paymentHeader = paymentHeader;
    }

    // Область формы оплаты
    private SelenideElement baseElement() {
        return $("fieldset");
    }

    // Поле ввода номера карты
    public SelenideElement cardNumberField() {
        return getInputByLabel("Номер карты");
    }

    // Поле ввода месяца срока действия карты
    public SelenideElement expiredMonthField() {
        return getInputByLabel("Месяц");
    }

    // Поле ввода года срока действия карты
    public SelenideElement expiredYearField() {
        return getInputByLabel("Год");
    }

    // Поле ввода Владельца карты
    public SelenideElement ownerField() {
        return getInputByLabel("Владелец");
    }

    //Поле ввода CVC/CVV кода
    public SelenideElement cvcField() {
        return getInputByLabel("CVC/CVV");
    }

    @Step("Проверка видимости ошибки поля ввода карты")
    public PaymentForm cardErrorCheck() {
        getErrorFromInput(cardNumberField())
                .shouldBe(visible)
                .shouldHave(text("Неверный формат"));
        return this;
    }

    @Step("Проверка видимости ошибки поля - Месяц")
    public PaymentForm monthErrorCheck() {
        getErrorFromInput(expiredMonthField())
                .shouldBe(visible)
                .shouldHave(text("Неверно указан срок действия карты"));
        return this;
    }

    @Step("Проверка видимости ошибки поля - Год")//ошибка поля ввода год
    public PaymentForm yearErrorCheck() {
        getErrorFromInput(expiredYearField())
                .shouldBe(visible)
                .shouldHave(text("Истёк срок действия карты"));
        return this;
    }

    @Step("Проверка видимости ошибки поля - Владелец")// Ошибка ввода Владельца карты
    public PaymentForm ownerErrorCheck() {
        getErrorFromInput(ownerField())
                .shouldBe(visible)
                .shouldHave(text("Поле обязательно для заполнения"));
        return this;
    }

    //ошибка ввода CVC/CVV кода
    @Step("Проверка видимости ошибки кода CVC/CVV")
    public SelenideElement cvcError() {
        return expiredYearField().closest("input__sub").shouldBe(visible);
    }

    private SelenideElement getLabel(String label) {
        return baseElement().$(byClassAndTextContaining("input__top", label));
    }

    private SelenideElement getInputByLabel(String label) {
        return getLabel(label).sibling(0).$x(".//input");
    }

    /**
     * Найти заголовок оплаты по карте/кредит по карте
     */
    private SelenideElement getHeaderElement() {
        return $(byClassAndTextContaining("h3", "heading", this.paymentHeader));
    }

    private SelenideElement continueButton() {
        return GetElement.buttonByText("Продолжить");
    }

    /**
     * Проверка видимости заголовка формы оплаты
     */
    public PaymentForm checkPaymentHeaderVisible() {
        Allure.step(String.format("Проверка видимости заголовка формы оплаты '{%s}'", this.paymentHeader),
                () -> getHeaderElement().shouldBe(visible));
        return this;
    }


    @Step("Заполнить поле Номер карты")
    public PaymentForm cardNumberInput(String card) {
        cardNumberField().shouldBe(visible).sendKeys(card);
        return this;
    }

    @Step("Заполнить поле Месяц")
    public PaymentForm expiredMonthInput(String month) {
        expiredMonthField().shouldBe(visible).sendKeys(month);
        return this;
    }

    @Step("Заполнить поле Год")
    public PaymentForm expiredYearInput(String Year) {
        expiredYearField().shouldBe(visible).sendKeys(Year);
        return this;
    }

    @Step("Заполнить поле Владелец")
    public PaymentForm expiredOwnerInput(String owner) {
        ownerField().shouldBe(visible).sendKeys(owner);
        return this;
    }

    @Step("Заполнить поле CVC/CVV")
    public PaymentForm cvcInput(String cvc) {
        cvcField().shouldBe(visible).sendKeys(cvc);
        return this;
    }

    @Step("Нажать кнопку Продолжить")
    public PaymentForm clickContinueButton() {
        continueButton().shouldBe(visible, enabled).click();
        return this;
    }

    @Step("Оплатить/Отправить запрос на кредит)")
    public static void sendPaymentForm(Function<BasePage, PaymentForm> formFunction, String card, String month, String year, String owner, String cvc) {
        BasePage basePage = new BasePage();
        PaymentForm paymentForm = formFunction.apply(basePage);
        paymentForm.checkPaymentHeaderVisible()
                .cardNumberInput(card)
                .expiredMonthInput(month)
                .expiredYearInput(year)
                .expiredOwnerInput(owner)
                .cvcInput(cvc)
                .clickContinueButton();
    }

    /**
     * Получить сообщение об ошибке, которое появляется при ошибочном вводе
     *
     * @param input - SelenideElement поля ввода
     */
    private SelenideElement getErrorFromInput(SelenideElement input) {
        return input.parent().sibling(0);
    }
}
