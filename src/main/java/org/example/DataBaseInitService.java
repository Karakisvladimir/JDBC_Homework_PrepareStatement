package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Statement;


public class DataBaseInitService  {
    public static void main(String[] args) {
        Database database = Database.getInstance();
        new DataBaseInitService().initDB(database);
    }


    public void initDB(Database database) {
        try {
            String initDbFilename = new Prefs().getString(Prefs.INIT_DB_SQL_FILE_PATH);
            String sql = String.join(
                    "\n",
                    Files.readAllLines(Paths.get(initDbFilename))
            );
ExecudeUpdate.executeUpdate(sql);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

