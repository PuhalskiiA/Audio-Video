package Utils.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DaoFactory {
    private static final String URL = "jdbc:postgresql://localhost:5432/AudioAndVideo";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "AnyoneMayDie";

    public static Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
