package ru.edu.pr04;
import java.util.*;

public class Order {
    private final Employee employee;
    private final List<OrderItem> items = new ArrayList<>();
    private OrderStatus status = OrderStatus.DRAFT;

    public Order(Employee employee) {
        this.employee = Objects.requireNonNull(employee);
    }

    // Добавление товара (с объединением повторной позиции)
    public void addProduct(Product product, int quantity) {
        if (status == OrderStatus.CONFIRMED) throw new IllegalStateException("Заказ уже подтвержден");

        // Ищем, есть ли уже такой товар
        for (OrderItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.increase(quantity); // Если есть — увеличиваем количество
                return;
            }
        }
        // Если нет — создаем новую позицию
        items.add(new OrderItem(product, quantity));
    }

    // Удаление позиции по ID
    public void removeProduct(String productId) {
        if (status == OrderStatus.CONFIRMED) throw new IllegalStateException("Заказ уже подтвержден");
        // Используем removeIf, чтобы удалить по ID
        items.removeIf(item -> item.getProduct().getId().equals(productId));
    }

    // Расчет итоговой суммы
    public double total() {
        double sum = 0;
        for (OrderItem item : items) {
            sum += item.getLineTotal();
        }
        return sum;
    }

    // Подтверждение заказа
    public void confirm() {
        if (status == OrderStatus.CONFIRMED) throw new IllegalStateException("Уже подтвержден");
        if (items.isEmpty()) throw new IllegalStateException("Нельзя подтвердить пустой заказ");
        this.status = OrderStatus.CONFIRMED;
    }

    public List<OrderItem> getItems() { return List.copyOf(items); }
    public OrderStatus getStatus() { return status; }
}