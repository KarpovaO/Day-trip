package utils.allure;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Allure;
import io.qameta.allure.listener.StepLifecycleListener;
import io.qameta.allure.model.StepResult;
import org.openqa.selenium.OutputType;

import java.io.ByteArrayInputStream;

public class AllureCallback implements StepLifecycleListener {
    @Override
    public void afterStepUpdate(StepResult result) {
        byte[] screenshot = Selenide.screenshot(OutputType.BYTES);
        if (screenshot != null) {
            Allure.addAttachment("screemshot", new ByteArrayInputStream(screenshot));
        }
    }
}
