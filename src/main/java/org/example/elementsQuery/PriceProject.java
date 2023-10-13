package org.example.elementsQuery;

public class PriceProject {
    private String name;
    private int price;

    public PriceProject(String name, int price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProjectPrices{" +
                "name='" + name + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
