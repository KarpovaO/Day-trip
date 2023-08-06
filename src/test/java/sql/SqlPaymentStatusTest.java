package sql;

import alerts.WindowAlert;
import com.codeborne.selenide.Selenide;
import elements.PaymentForm;
import enitity.PaymentStatus;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import page.BasePage;
import utils.sql.PostgresClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static enitity.PaymentStatus.APPROVED;
import static enitity.PaymentStatus.DECLINED;
import static org.junit.jupiter.api.Assertions.*;
import static page.OpenPage.loginPage;
import static utils.sql.SqlUser.ADMIN;

public class SqlPaymentStatusTest {

    private final WindowAlert windowAlert = new WindowAlert();
    private static final PostgresClient query = new PostgresClient(ADMIN);

    @BeforeEach
    void beforeAll() {
        loginPage();
    }

    @DisplayName("Проверка что в базе отобразился платеж в статусе APPROVED для обычной покупки")
    @Test
    void acceptCheck() {
        commonSteps(APPROVED, false);
    }

    @DisplayName("Проверка что в базе отобразился платеж в статусе DECLINED для обычной покупки")
    @Test
    void declineCheck() {
        commonSteps(DECLINED, false);
    }

    @DisplayName("Проверка что в базе отобразился платеж в статусе APPROVED для покупки в кредит")
    @Test
    void acceptCreditCheck() {
        commonSteps(APPROVED, true);
    }

    @DisplayName("Проверка что в базе отобразился платеж в статусе DECLINED для покупки в кредит")
    @Test
    void declineCreditCheck() {
        commonSteps(DECLINED, true);
    }

    /**
     * Метод содержащий базовые шаги для тестирования
     *
     * @param status     - статус в БД
     */
    private void commonSteps(PaymentStatus status,  Boolean credit) {
        LocalDateTime timeStart = LocalDateTime.now();
        PaymentForm.sendPaymentForm(credit ? BasePage::buyingCreditButtonClick : BasePage::buyButtonClick,
                status.getAccount(), "08", "23", "SERGEY RUMYANOV", "333");
        windowAlert.isVisibleAlert(status.getMsg());
        Selenide.sleep(1000);
        LocalDateTime timeEnd = LocalDateTime.now();
        List<Map<String, Object>> maps = getRowFromPaymentEntityByTimeAndStatus(credit, timeStart, timeEnd, status.name());

        assertAll(
                () -> assertEquals(1, maps.size(), "В таблице найдено больше 1 записи"),
                () -> assertNotNull(maps.get(0).get("id"))
        );
    }

    @Step("Найти запись покупки по времени в интервале времени в статусе {status}")
    private List<Map<String, Object>> getRowFromPaymentEntityByTimeAndStatus(Boolean credit, LocalDateTime timeStart, LocalDateTime timeEnd, String status) {
        String tableName = credit ? "credit_request_entity" : "payment_entity";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return query.executeQuery(String.format("SELECT * FROM %s\n" +
                "WHERE created > to_timestamp('%s', 'dd-mm-yyyy hh24:mi:ss')\n" +
                "AND created < to_timestamp('%s', 'dd-mm-yyyy hh24:mi:ss')\n" +
                "AND status = '%s'", tableName, timeStart.format(formatter), timeEnd.format(formatter), status));
    }

    @AfterEach
    void afterEach() {
        closeWebDriver();
    }

}
