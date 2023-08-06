package alerts;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static utils.elements.ByAttribute.*;

public class WindowAlert {

    public  ElementsCollection alertElement() {

        return $$(byClassContaining("notification_status"));
    }

    public WindowAlert isVisibleAlert(String text) {
         alertElement().findBy(visible).text().contains(text);
         return this;
    }

}
