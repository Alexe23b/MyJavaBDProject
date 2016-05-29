package db;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mda on 5/23/16.
 */
public class DBUtilsSample {

    private static SimpleDateFormat dateFormat ;

    static Connection getDBConnection(){

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

    static void createDbUserTable() throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;

        String createTableSQL = "CREATE TABLE DBUSER("
                + "USER_ID INTEGER NOT NULL, "
                + "USERNAME VARCHAR(20) NOT NULL, "
                + "CREATED_BY VARCHAR(20) NOT NULL, "
                + "CREATED_DATE DATE NOT NULL, " + "PRIMARY KEY (USER_ID) "
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
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    static void insert() {
        String insertTableSQL = "INSERT INTO DBUSER"
                + "(USER_ID, USERNAME, CREATED_BY, CREATED_DATE) " + "VALUES"
                + "(1,'mkyong','system', " + "to_date('"
                + getCurrentTimeStamp() + "', 'yyyy/mm/dd hh24:mi:ss'))";


        try {
            Connection dbConnection = getDBConnection();
            Statement statement = dbConnection.createStatement();

            // выполняем запрос delete SQL
            statement.execute(insertTableSQL);
            System.out.println("Record is inserted into DBUSER table!");


            ResultSet rs = statement.executeQuery("SELECT last_insert_id()");
            if (rs.next()){
                int key = rs.getInt(1);
                System.out.println(key);
            } else{
                System.out.println("Error getting key");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    static void delete(){
        String deleteTableSQL = "DELETE DBUSER WHERE USER_ID = 1";

        try {
            Connection dbConnection = getDBConnection();
            Statement statement = dbConnection.createStatement();

            // выполняем запрос delete SQL
            statement.execute(deleteTableSQL);
            System.out.println("Record is deleted from DBUSER table!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static void update(){
        String updateTableSQL = "UPDATE DBUSER SET USERNAME = 'mkyong_new' WHERE USER_ID = 1";

        try {
            Connection dbConnection = getDBConnection();
            Statement statement = dbConnection.createStatement();

            // выполняем запрос update SQL
            statement.execute(updateTableSQL);

            System.out.println("Record is updated to DBUSER table!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String getCurrentTimeStamp() {
        Date today = new Date();
        return dateFormat.format(today);
    }

    public void updateCoffeeSales(HashMap<String, Integer> salesForWeek)
            throws SQLException {

        PreparedStatement updateSales = null;
        PreparedStatement updateTotal = null;

        String dbName = "tempName";
        String updateString =
                "update " + dbName + ".COFFEES " +
                        "set SALES = ? where COF_NAME = ?";

        String updateStatement =
                "update " + dbName + ".COFFEES " +
                        "set TOTAL = TOTAL + ? " +
                        "where COF_NAME = ?";

        Connection con = getDBConnection();
        try {
            con.setAutoCommit(false);
            updateSales = con.prepareStatement(updateString);
            updateTotal = con.prepareStatement(updateStatement);

            for (Map.Entry<String, Integer> e : salesForWeek.entrySet()) {
                updateSales.setInt(1, e.getValue().intValue());
                updateSales.setString(2, e.getKey());
                updateSales.executeUpdate();
                updateTotal.setInt(1, e.getValue().intValue());
                updateTotal.setString(2, e.getKey());
                updateTotal.executeUpdate();
                con.commit();
            }
        } catch (SQLException e ) {
            e.printStackTrace();
            if (con != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    con.rollback();
                } catch(SQLException excep) {
                    excep.printStackTrace();
                }
            }
        } finally {
            if (updateSales != null) {
                updateSales.close();
            }
            if (updateTotal != null) {
                updateTotal.close();
            }
            con.setAutoCommit(true);
        }
    }
}
