package org.example;

public class Main {
    public static void main(String[] args) {
        Database database = Database.getInstance();

        DatabaseQueryService databaseQueryService = new DatabaseQueryService(database);
        System.out.println("databaseQueryService.printProjectPrices() = " + databaseQueryService.printProjectPrices());
        System.out.println("databaseQueryService.findLongestProject() = " + databaseQueryService.findLongestProject());
        System.out.println("databaseQueryService.findMaxSalaryWorker() = " + databaseQueryService.findMaxSalaryWorker());
        System.out.println("databaseQueryService.findMaxProjectsClient() = " + databaseQueryService.findMaxProjectsClient());
        // I have print in the methods
        databaseQueryService.printProjectPrices();
        databaseQueryService.findYoungestEldestWorkers();
    }
}


