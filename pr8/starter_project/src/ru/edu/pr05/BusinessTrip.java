package ru.edu.pr05;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BusinessTrip {
    private final String id;
    private final LocalDate start;
    private final LocalDate end;
    private TripStatus status = TripStatus.DRAFT;
    private String rejectionReason;

    // Константы правил (Вариант 2)
    private static final int MIN_REASON_LENGTH = 10;
    private static final int CANCEL_DAYS_LIMIT = 15;

    public BusinessTrip(String id, LocalDate start, LocalDate end) {
        // Проверка дат и id
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID не может быть пустым");
        if (start == null || end == null) throw new IllegalArgumentException("Даты не могут быть null");
        if (start.isAfter(end)) throw new IllegalArgumentException("Дата начала не может быть позже даты окончания");

        this.id = id;
        this.start = start;
        this.end = end;
    }

    public void submit() {
        if (status != TripStatus.DRAFT) {
            throw new IllegalStateException("Подать заявку можно только из статуса DRAFT. Текущий: " + status);
        }
        this.status = TripStatus.SUBMITTED;
    }

    public void approve() {
        if (status != TripStatus.SUBMITTED) {
            throw new IllegalStateException("Одобрить можно только из статуса SUBMITTED. Текущий: " + status);
        }
        this.status = TripStatus.APPROVED;
    }

    public void reject(String reason) {
        if (status != TripStatus.SUBMITTED) {
            throw new IllegalStateException("Отклонить можно только из статуса SUBMITTED. Текущий: " + status);
        }
        if (reason == null || reason.trim().length() < MIN_REASON_LENGTH) {
            throw new IllegalArgumentException("Причина отклонения должна быть не менее " + MIN_REASON_LENGTH + " символов");
        }
        this.rejectionReason = reason;
        this.status = TripStatus.REJECTED;
    }

    public void cancel(LocalDate today) {
        if (status == TripStatus.COMPLETED) {
            throw new IllegalStateException("Нельзя отменить завершенную командировку");
        }
        if (status != TripStatus.DRAFT && status != TripStatus.SUBMITTED && status != TripStatus.APPROVED) {
            throw new IllegalStateException("Отмена невозможна из статуса " + status);
        }

        // Правило варианта 2: до начала должно быть > 15 дней
        long daysUntilStart = ChronoUnit.DAYS.between(today, start);
        if (daysUntilStart <= CANCEL_DAYS_LIMIT) {
            throw new IllegalStateException("Отмена невозможна: до начала командировки осталось " + daysUntilStart + " дн. (лимит: > " + CANCEL_DAYS_LIMIT + ")");
        }

        this.status = TripStatus.CANCELLED;
    }

    public void complete(LocalDate today) {
        if (status != TripStatus.APPROVED) {
            throw new IllegalStateException("Завершить можно только из статуса APPROVED. Текущий: " + status);
        }
        if (!today.isAfter(end)) {
            throw new IllegalStateException("Нельзя завершить командировку до даты её окончания (" + end + ")");
        }
        this.status = TripStatus.COMPLETED;
    }

    public TripStatus getStatus() { return status; }
    public String getRejectionReason() { return rejectionReason; }
}