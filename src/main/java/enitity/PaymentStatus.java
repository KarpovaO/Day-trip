package enitity;

public enum PaymentStatus {
    APPROVED("4444 4444 4444 4441", "Успешно"),
    DECLINED("4444 4444 4444 4442", "Ошибка");

    private final String account;
    private final String msg;

    PaymentStatus(String account, String msg) {
        this.account = account;
        this.msg = msg;
    }

    public String getAccount() {
        return account;
    }

    public String getMsg() {
        return msg;
    }
}
