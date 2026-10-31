package ru.edu.pr03;

public class ApprovalRouter {

    public static final long FINANCE_LIMIT = 500_000L;
    public static final long RISK_PRIORITY_LIMIT = 15_000L; // Вариант 2

    public ApprovalRoute route(long amount, boolean urgent, RiskLevel risk, boolean containsPersonalData) {
        // 1. Самое строгое правило (высший приоритет)
        if (containsPersonalData) {
            return ApprovalRoute.SECURITY_REVIEW;
        }

        // 2. Риск HIGH (но с учетом твоего варианта 2 - сумма должна быть >= 15000)
        if (risk == RiskLevel.HIGH && amount >= RISK_PRIORITY_LIMIT) {
            return ApprovalRoute.DIRECTOR_REVIEW;
        }

        // 3. Финансовый порог
        if (amount >= FINANCE_LIMIT) {
            return ApprovalRoute.FINANCE_REVIEW;
        }

        // 4. Срочность (проверяем только если не сработали правила выше)
        if (urgent) {
            return ApprovalRoute.FAST_TRACK;
        }

        // 5. Все остальные случаи
        return ApprovalRoute.STANDARD;
    }
}