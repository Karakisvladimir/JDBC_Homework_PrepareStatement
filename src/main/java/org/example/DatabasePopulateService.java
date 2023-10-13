package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DatabasePopulateService {
    public static void main(String[] args) {
        Database database = Database.getInstance();
        new DatabasePopulateService().initDB(database);
        String insertSQL = String.format(
                "INSERT INTO client(name) VALUES('%s')",
                "Vasiy Rybkin");
        ExecudeUpdate.executeUpdate(insertSQL);
    }

    public void initDB(Database database) {
        try {
            String initDbFilename = new Prefs().getString(Prefs.INIT_DB_SQL_POPULATE_DB);
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
