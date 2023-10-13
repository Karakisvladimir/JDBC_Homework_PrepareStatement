package org.example;

import java.sql.Connection;
import java.sql.Statement;



public class ExecudeUpdate {

    public static int executeUpdate(String sql) {

        try (Statement st = Database.getConnection().createStatement()) {
            return st.executeUpdate(sql);
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }
}