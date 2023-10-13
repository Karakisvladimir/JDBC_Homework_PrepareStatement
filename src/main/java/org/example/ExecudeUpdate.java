package org.example;

import java.sql.Connection;
import java.sql.Statement;

public class ExecudeUpdate {
    private static Connection connection;
    public static int executeUpdate(String sql) {
        try (Statement st = connection.createStatement()) {
            return st.executeUpdate(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }
}