package ru.edu.pr04;

public class OrderService {

    // Вариант 2: Лимит общей суммы
    public static final double MAX_TOTAL_LIMIT = 15_000.0;

    public Order createOrder(Employee e) { return new Order(e); }

    // Валидация перед подтверждением
    public void validateForConfirm(Order order) {
        if (order.total() > MAX_TOTAL_LIMIT) {
            throw new IllegalStateException("Сумма заказа (" + order.total() + ") превышает лимит " + MAX_TOTAL_LIMIT);
        }
    }
}