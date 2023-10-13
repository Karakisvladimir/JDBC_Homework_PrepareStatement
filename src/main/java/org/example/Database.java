package org.example;


import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private static final Database INSTANCE = new Database();
    private Connection connection;

    private Database() {
        try {
            String connectionUrl = new Prefs().getString(Prefs.DB_URL);
            connection = DriverManager.getConnection(connectionUrl);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Database getInstance() {
        return INSTANCE;
    }


    public Connection getConnection() {
        return connection;
    }

}