package ru.edu.pr04;

public class OrderItem {
    private final Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        this.product = product;
        setQuantity(quantity);
    }

    // Проверка количества (от 1 до 100)
    private void setQuantity(int quantity) {
        if (quantity < 1 || quantity > 100) {
            throw new IllegalArgumentException("Количество должно быть от 1 до 100");
        }
        this.quantity = quantity;
    }

    // Увеличение количества (для объединения дубликатов)
    public void increase(int delta) {
        if (delta <= 0) throw new IllegalArgumentException("Дельта должна быть > 0");
        setQuantity(this.quantity + delta); // вызовет проверку на макс. 100
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }

    // Стоимость строки
    public double getLineTotal() {
        return product.getPrice() * quantity;
    }
}