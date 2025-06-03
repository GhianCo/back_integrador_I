package shared.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn {
    private static String driver = null;
    private static String user = null;
    private static String password = null;
    private static String url = null;

    static {
        try {
            url = "jdbc:mysql://localhost:3306/integrador_1?autoReconnect=true&useSSL=false";
            driver = "com.mysql.cj.jdbc.Driver";
            user = "root";
            password = "";

            Class.forName(driver);

        } catch (ClassNotFoundException e) {
            System.out.println(Util.error1);
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Error:" + e);
        }
        return connection;
    }
}
