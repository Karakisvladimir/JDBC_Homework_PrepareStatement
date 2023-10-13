package org.example;

import java.sql.PreparedStatement;
import java.sql.Statement;


public class ExecudeUpdate {

    public static int executeUpdate(String sql) {

        try (PreparedStatement ps = Database.getConnection().prepareStatement(sql)) {
            return ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }
}

