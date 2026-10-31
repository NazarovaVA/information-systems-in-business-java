package ru.edu.pr05;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        LocalDate today = LocalDate.of(2026, 10, 1);
        System.out.println("Запуск проверок ПР8:");

        // 1. Успешный сценарий
        BusinessTrip trip1 = new BusinessTrip("T-01", today.plusDays(20), today.plusDays(25));
        trip1.submit();
        trip1.approve();
        trip1.complete(today.plusDays(26));
        check(trip1.getStatus() == TripStatus.COMPLETED, "Успешный цикл (DRAFT -> COMPLETED)");

        // 2. Ошибка: попытка одобрить из DRAFT
        BusinessTrip trip2 = new BusinessTrip("T-02", today.plusDays(20), today.plusDays(25));
        try {
            trip2.approve();
            check(false, "Одобрение из DRAFT должно упасть");
        } catch (IllegalStateException e) {
            check(true, "Одобрение из DRAFT заблокировано");
        }

        // 3. Ошибка: причина отклонения слишком короткая
        BusinessTrip trip3 = new BusinessTrip("T-03", today.plusDays(20), today.plusDays(25));
        trip3.submit();
        try {
            trip3.reject("Мало"); // 4 символа < 10
            check(false, "Короткая причина должна упасть");
        } catch (IllegalArgumentException e) {
            check(true, "Короткая причина отклонения заблокирована");
        }

        // 4. Вариант 2: Отмена разрешена (до начала > 15 дней)
        BusinessTrip trip4 = new BusinessTrip("T-04", today.plusDays(16), today.plusDays(20));
        trip4.cancel(today);
        check(trip4.getStatus() == TripStatus.CANCELLED, "Отмена за 16 дней (разрешено)");

        // 5. Вариант 2: Отмена запрещена (до начала <= 15 дней)
        BusinessTrip trip5 = new BusinessTrip("T-05", today.plusDays(15), today.plusDays(20));
        try {
            trip5.cancel(today);
            check(false, "Отмена за 15 дней должна упасть");
        } catch (IllegalStateException e) {
            check(true, "Отмена за 15 дней (Вариант 2) заблокирована");
        }

        // 6. Ошибка: завершение до конца командировки
        BusinessTrip trip6 = new BusinessTrip("T-06", today.plusDays(1), today.plusDays(10));
        trip6.submit();
        trip6.approve();
        try {
            trip6.complete(today.plusDays(5)); // сегодня до даты окончания
            check(false, "Завершение раньше времени должно упасть");
        } catch (IllegalStateException e) {
            check(true, "Завершение до end date заблокировано");
        }
    }

    public static void check(boolean condition, String testName) {
        System.out.println((condition ? "PASS: " : "FAIL: ") + testName);
    }
}