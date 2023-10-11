package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseQueryService {
    private Database database;

    public DatabaseQueryService(Database database) {
        this.database = database;
    }

    public void printNameClient() {
        try (Statement st = database.getConnection().createStatement()) {
            try (ResultSet rs = st.executeQuery("SELECT name  FROM client")) {
                while (rs.next()) {
                    String name = rs.getString("name");
                    System.out.println("name client = " + name);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void find_longest_project() {
        try (Statement st = database.getConnection().createStatement()) {
            String sql = "SELECT c.name,\n" +
                    "    DATEDIFF('MONTH', p.START_DATE, p.FINISH_DATE) AS month_count\n" +
                    "    FROM client c\n" +
                    "    JOIN project p ON p.client_id = c.id\n" +
                    "    WHERE DATEDIFF('MONTH', p.START_DATE, p.FINISH_DATE) = (\n" +
                    "    SELECT MAX(project_duration)\n" +
                    "    FROM (SELECT DATEDIFF('MONTH', START_DATE, FINISH_DATE)\n" +
                    "    AS project_duration FROM project) AS project_duration)";
            try (ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    String name = rs.getString(1);
                    int months = rs.getInt(2);
                    System.out.println(name + ": " + months);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void find_max_projects_client() {
        try (Statement st = database.getConnection().createStatement()) {
            String sql = "SELECT c.name, COUNT(p.client_id) AS projects_client FROM client c \n" +
                    "JOIN project p ON p.client_id = c.id\n" +
                    "GROUP BY client_id\n" +
                    "HAVING projects_client = (\n" +
                    "SELECT MAX(proj) FROM (\n" +
                    "SELECT COUNT(client_id) AS proj\n" +
                    "FROM project\n" +
                    "GROUP BY client_id))";

            try (ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    String clientName = rs.getString("name");
                    int projectCount = rs.getInt("projects_client");
                    System.out.println("Client: " + clientName + " has the most projects with " + projectCount + " projects.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void find_max_salary_worker() {


        try (Statement st = database.getConnection().createStatement()) {
            try (ResultSet rs = st.executeQuery("SELECT name, salary FROM worker\n" +
                    "WHERE salary = (SELECT MAX(salary) FROM worker);")) {
                while (rs.next()) {
                    System.out.println("Name: " + rs.getString("name") + " salary: " + rs.getInt("salary"));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public <YoungestEldestWorkers> List<YoungestEldestWorkers>  findYoungestEldestWorkers() {
        List<YoungestEldestWorkers> result = new ArrayList<>();

        try {
            String sql = Files.readString(Path.of("./sql/find_youngest_eldest_workers.sql"));


            try (Statement statement = database.getConnection().createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    String type = resultSet.getString("type");
                    String name = resultSet.getString("name");
                    String birthday = resultSet.getString("birthday");

                    YoungestEldestWorkers worker = new YoungestEldestWorkers(type, name, birthday);

                    result.add(worker);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }


}


//    executeUpdate можна помістити в окремий клас
//    також в цей клас додати метод executeQuery який буде мати логіку ( st.executeQuery())
//        концептуально він  схожий на executeUpdate()
//        find_youngest_eldest_workers()  не вірно. sql він має зчитувати з файлу

