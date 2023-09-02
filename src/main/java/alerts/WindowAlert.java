package alerts;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static utils.elements.ByAttribute.*;

public class WindowAlert {

    public  ElementsCollection alertElement() {

        return $$(byClassContaining("notification__title"));
    }

    public void isVisibleAlert(String text) {
        //boolean b = alertElement().findBy(visible).text().contains(text);
        SelenideElement el = alertElement().findBy(visible);
        String txt = el.text();
        boolean b = txt.contains(text);
        assert(b);
    }

}
