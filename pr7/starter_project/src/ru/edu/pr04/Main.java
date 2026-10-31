package ru.edu.pr04;

public class Main {
    public static void main(String[] args) {
        OrderService service = new OrderService();
        Employee emp = new Employee("E1", "Иван");
        Order order = service.createOrder(emp);

        Product p1 = new Product("P1", "Монитор", 5000);
        Product p2 = new Product("P2", "Клавиатура", 2000);

        System.out.println("Запуск проверок ПР7:");

        // 1. Объединение дубликатов
        order.addProduct(p1, 1);
        order.addProduct(p1, 2); // Должно стать 3
        check(order.getItems().get(0).getQuantity() == 3, "Объединение дубликатов (стало 3 шт.)");

        // 2. Удаление
        order.removeProduct("P1");
        check(order.getItems().isEmpty(), "Удаление позиции по ID");

        // 3. Лимит суммы (Твой вариант 2: лимит 15 000)
        // Сумма будет 15000 (3 монитора по 5000). Это ровно лимит, должно пройти.
        order.addProduct(p1, 3);
        try {
            service.validateForConfirm(order);
            order.confirm();
            check(true, "Заказ на 15000 успешно подтвержден (граница)");
        } catch (Exception e) {
            check(false, "Заказ на 15000 не должен падать: " + e.getMessage());
        }

        // 4. Запрет изменения после подтверждения
        try {
            order.addProduct(p2, 1); // Пытаемся добавить
            check(false, "Добавление после confirm должно быть заблокировано");
        } catch (IllegalStateException e) {
            check(true, "Добавление после confirm заблокировано");
        }

        // 5. Превышение лимита
        Order bigOrder = service.createOrder(emp);
        bigOrder.addProduct(p1, 4); // 20000 (превышает 15000)
        try {
            service.validateForConfirm(bigOrder);
            check(false, "Превышение лимита не сработало");
        } catch (IllegalStateException e) {
            check(true, "Превышение лимита 15000 успешно заблокировано");
        }

        // 6. Неверная цена товара
        try {
            new Product("P3", "Брак", -100);
            check(false, "Отрицательная цена должна падать");
        } catch (IllegalArgumentException e) {
            check(true, "Отрицательная цена заблокирована");
        }
    }

    public static void check(boolean condition, String testName) {
        System.out.println((condition ? "PASS: " : "FAIL: ") + testName);
    }
}