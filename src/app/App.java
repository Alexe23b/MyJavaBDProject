package app;

import db.connections.DBUtils;

import java.sql.Connection;

/**
 * Created by alexe on 13.05.2016.
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Start");
        Connection dbConnection = DBUtils.getDBConnection();

        DBUtils.createAdressTable(dbConnection);
    }
}
