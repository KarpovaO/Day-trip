package sql;

import alerts.WindowAlert;
import com.codeborne.selenide.Selenide;
import elements.PaymentForm;
import enitity.PaymentStatus;
import io.qameta.allure.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import page.BasePage;
import utils.sql.PostgresClient;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static page.OpenPage.loginPage;
import static utils.sql.SqlUser.ADMIN;

public class SqlAmountTest {
    private final BasePage basePage = new BasePage();
    private final WindowAlert windowAlert = new WindowAlert();
    private static final PostgresClient query = new PostgresClient(ADMIN);

    @BeforeEach
    void beforeAll() {
        loginPage();
    }

    @ParameterizedTest(name = "Проверка суммы для статуса {0}")
    @EnumSource(PaymentStatus.class)
    void checkAmount(PaymentStatus status) {
        commonSteps(status);
    }

    /**
     * Метод содержащий базовые шаги для тестирования
     *
     * @param status - статус в БД
     */
    private void commonSteps(PaymentStatus status) {
        LocalDateTime timeStart = LocalDateTime.now(ZoneOffset.UTC);
        Integer price = basePage.getTripPrice();
        PaymentForm.sendPaymentForm(BasePage::buyButtonClick,
                status.getAccount(), "08", "24", "SERGEY RUMYANOV", "333");
        windowAlert.isVisibleAlert(status.getMsg());
        Selenide.sleep(1000);
        LocalDateTime timeEnd = LocalDateTime.now(ZoneOffset.UTC);
        Integer amount = getRowFromPaymentEntityByTimeAndStatus(timeStart, timeEnd, status.name());

        assertEquals(price, amount, "Стоимость путевки не соотвествует ожидаемой");
    }

    @Step("Найти запись покупки по времени в интервале времени в статусе {status}")
    private Integer getRowFromPaymentEntityByTimeAndStatus(LocalDateTime timeStart, LocalDateTime timeEnd, String status) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String sql = String.format("SELECT amount FROM payment_entity\n" +
                "WHERE created > to_timestamp('%s', 'dd-mm-yyyy hh24:mi:ss')\n" +
                "AND created < to_timestamp('%s', 'dd-mm-yyyy hh24:mi:ss')\n" +
                "AND status = '%s'", timeStart.format(formatter), timeEnd.format(formatter), status);

        return Integer.parseInt(query.executeQuery(sql).get(0).get("amount").toString());
    }
}
