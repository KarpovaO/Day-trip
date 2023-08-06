package utils.sql;

import org.postgresql.Driver;

import java.sql.*;
import java.util.*;

public class PostgresClient {
    private Connection connection;
    private final SqlUser user;

    public PostgresClient(SqlUser user) {
        this.user = user;
        Properties properties = new Properties();
        properties.setProperty("user", user.getUserName());
        properties.setProperty("password", user.getPassword());
        try {
            connection = new Driver().connect(user.getUrl(), properties);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Map<String, Object>> executeQuery(String sqlQuery){
        try {
            List<Map<String, Object>> result = new ArrayList<>();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            ResultSetMetaData metaData = resultSet.getMetaData();
            while (resultSet.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    row.put(metaData.getColumnName(i), resultSet.getString(i));
                }
                result.add(row);
            }

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
