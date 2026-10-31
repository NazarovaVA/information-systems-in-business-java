package ru.edu.pr03;

public class Main {
    public static void main(String[] args) {
        ApprovalRouter router = new ApprovalRouter();

        System.out.println("Запуск собственных проверок:");

        // 1. Проверка персональных данных (наивысший приоритет)
        check(router.route(10_000, true, RiskLevel.HIGH, true) == ApprovalRoute.SECURITY_REVIEW,
                "Персональные данные (должны перекрыть всё)");

        // 2. Проверка твоего варианта 2: Риск HIGH, но сумма 14999 (меньше порога 15000)
        // Должно уйти в FAST_TRACK (так как urgent=true), а не к директору
        check(router.route(14_999, true, RiskLevel.HIGH, false) == ApprovalRoute.FAST_TRACK,
                "Риск HIGH, но сумма 14999 (не доходит до директора)");

        // 3. Проверка твоего варианта 2: Риск HIGH, сумма ровно 15000 (попадает к директору)
        check(router.route(15_000, false, RiskLevel.HIGH, false) == ApprovalRoute.DIRECTOR_REVIEW,
                "Риск HIGH и сумма ровно 15000 (идет к директору)");

        // 4. Нижняя граница финансов (499 999) -> должно уйти в FAST_TRACK
        check(router.route(499_999, true, RiskLevel.LOW, false) == ApprovalRoute.FAST_TRACK,
                "Сумма 499999 и срочно (FAST_TRACK)");

        // 5. Ровно 500 000 -> должно уйти в FINANCE_REVIEW
        check(router.route(500_000, false, RiskLevel.LOW, false) == ApprovalRoute.FINANCE_REVIEW,
                "Сумма ровно 500000 (FINANCE_REVIEW)");

        // 6. Выше порога (500 001) -> должно уйти в FINANCE_REVIEW
        check(router.route(500_001, false, RiskLevel.LOW, false) == ApprovalRoute.FINANCE_REVIEW,
                "Сумма 500001 (FINANCE_REVIEW)");
    }

    public static void check(boolean condition, String testName) {
        if (condition) {
            System.out.println("PASS: " + testName);
        } else {
            System.out.println("FAIL: " + testName);
        }
    }
}