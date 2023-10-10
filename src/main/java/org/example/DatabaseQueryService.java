package org.example;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

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
    public void find_max_projects_client(){
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

public  void find_max_salary_worker(){


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
    public void find_youngest_eldest_workers() {
        try (Statement st = database.getConnection().createStatement()) {
            try (ResultSet rs = st.executeQuery("SELECT 'YOUNGEST' AS type, name, birthday\n" +
                    "FROM worker\n" +
                    "WHERE birthday= (SELECT MAX(birthday) FROM worker)\n" +
                    "UNION ALL\n" +
                    "SELECT 'OLDEST' AS type, name, birthday\n" +
                    "FROM worker\n" +
                    "WHERE birthday= (SELECT MIN(birthday) FROM worker);")) {
                while (rs.next()) {
                    String type = rs.getString("type");
                    String name = rs.getString("name");
                    Date birthday = rs.getDate("birthday");

                    System.out.println(type + ": " + name + " (" + birthday + ")");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        Database database = Database.getInstance();

        DatabaseQueryService databaseQueryService = new DatabaseQueryService(database);
        //databaseQueryService.printNameClient();
        //databaseQueryService.find_longest_project();
        // databaseQueryService.find_max_projects_client();
        //databaseQueryService.find_max_salary_worker();
        databaseQueryService.find_youngest_eldest_workers();
    }
}

