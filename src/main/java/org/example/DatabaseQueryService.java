package org.example;

import org.example.elementsQuery.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public List<MaxSalaryWorker> findMaxSalaryWorker() {
        List<MaxSalaryWorker> result = new ArrayList<>();

        try {
            String sql = Files.readString(Path.of("./sql/find_max_salary_worker.sql"));

            try (Statement statement = database.getConnection().createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int salary = resultSet.getInt("salary");

                    MaxSalaryWorker worker = new MaxSalaryWorker(name, salary);

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

    public List<MaxProjectCountClient> findMaxProjectsClient() {
        List<MaxProjectCountClient> result = new ArrayList<>();

        try {

            String sql = Files.readString(Path.of("./sql/find_max_projects_client.sql"));

            try (Statement statement = database.getConnection().createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int projectCount = resultSet.getInt("projects_client");

                    MaxProjectCountClient client = new MaxProjectCountClient(name, projectCount);

                    result.add(client);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
               return result;
    }

    public List<LongestProject> findLongestProject() {
        List<LongestProject> result = new ArrayList<>();

        try {
            String sql = Files.readString(Path.of("./sql/find_longest_project.sql"));


            try (Statement statement = database.getConnection().createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int monthCount = resultSet.getInt("month_count");

                    LongestProject project = new LongestProject(name, monthCount);

                    result.add(project);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
//інший засіб ведення путі через джойни та використання константи
    public List<YoungestEldestWorkers> findYoungestEldestWorkers() {
        List<YoungestEldestWorkers> result = new ArrayList<>();

        try {
            String initDbFilename = new Prefs().getString(Prefs.SELECT_YOUNGEST_ELDEST);
            String sql = String.join(
                    "\n",
                    Files.readAllLines(Paths.get(initDbFilename))
            );


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

        System.out.println(result);
        return result;
    }
    //інший засіб ведення путі через джойни та використання константи
    public List<PriceProject> printProjectPrices() {
        List<PriceProject> result = new ArrayList<>();

        try {
            String initDbFilename = new Prefs().getString(Prefs.PRINT_PROJECT_PRICES);
            String sql = String.join(
                    "\n",
                    Files.readAllLines(Paths.get(initDbFilename))
            );


            try (Statement statement = database.getConnection().createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    int price = resultSet.getInt("price");

                    PriceProject project = new PriceProject(name, price);

                    result.add(project);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(result);
        return result;

    }
}



