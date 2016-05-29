package db.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

/**
 * Created by mda on 5/23/16.
 */
public class DBUtils {

    private static SimpleDateFormat dateFormat ;

    public static Connection getDBConnection(){

        String urlPostgress = "jdbc:postgresql://localhost:5432/tac_addressbook";
        String login = "postgres";
        String password = "postgres_password";

        return getDBConnection("org.postgresql.Driver", urlPostgress, login, password);


//        return getDBConnection("com.mysql.jdbc.Driver", "jdbc:mysql://hostname:port/dbname","username", "password");
//        return getDBConnection("oracle.jdbc.driver.OracleDriver","jdbc:oracle:thin:@localhost:1521:mkyong","username","password");
    }

    private static Connection getDBConnection(String dbDriver,
                                              String dbConnection,
                                              String dbUser,
                                              String dbPassword) {
        Connection connection = null;
        try {
            Class.forName(dbDriver);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            connection = DriverManager.getConnection(dbConnection, dbUser, dbPassword);
            return connection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }
    public static void createAdressTable(Connection dbConnection) {
        Statement statement = null;

        String createTableSQL = "CREATE TABLE address("
                + "address_id INTEGER NOT NULL, "
                + "country VARCHAR(30) NOT NULL, "
                + "city VARCHAR(20) NOT NULL, "
                + "street VARCHAR(250) NOT NULL, "
                + "house_number INTEGER NOT NULL, "
                + "house_suffix VARCHAR(20) NOT NULL, "
                + "post_code INTEGER NOT NULL, "
                + "PRIMARY KEY (address_id) "
                + ")";

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            // выполнить SQL запрос
            statement.execute(createTableSQL);
            System.out.println("Table \"dbuser\" is created!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
