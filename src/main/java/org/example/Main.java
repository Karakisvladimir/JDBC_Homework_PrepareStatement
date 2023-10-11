package org.example;

public class Main {
    public static void main(String[] args) {
        Database database = Database.getInstance();

        DatabaseQueryService databaseQueryService = new DatabaseQueryService(database);
        databaseQueryService.printNameClient();
        databaseQueryService.find_longest_project();
        databaseQueryService.find_max_projects_client();
        databaseQueryService.find_max_salary_worker();
        System.out.println("FindYoungestEldestWorkers() = " + databaseQueryService.findYoungestEldestWorkers());
    }
}
