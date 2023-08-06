package page;

import Config.BaseConfig;
import com.codeborne.selenide.Configuration;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.open;

public class OpenPage {
    @Step("Открыть страницу приложения")
    public static void loginPage() {
        open(BaseConfig.getUrl());
        Configuration.timeout = 10000;
    }
}