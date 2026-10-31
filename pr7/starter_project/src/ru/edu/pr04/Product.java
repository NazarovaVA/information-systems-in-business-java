package ru.edu.pr04;

public class Product {
    private final String id;
    private final String name;
    private final double price;

    public Product(String id, String name, double price) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID товара не может быть пустым");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Название не может быть пустым");
        if (price <= 0) throw new IllegalArgumentException("Цена должна быть положительной");

        this.id = id;
        this.name = name;
        this.price = price;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
}