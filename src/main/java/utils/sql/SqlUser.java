package utils.sql;

public enum SqlUser {
    ADMIN("jdbc:postgresql://localhost:5432/postgres", "postgres", "pass");

    private final String url;
    private final String userName;
    private final String password;

    SqlUser(String url, String userName, String password) {
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
