package ru.edu.pr02;

public class TimesheetAnalyzer {

    private static final int EXPECTED_VALUES = 5;  // Ровно 5 дней
    private static final int MIN_HOURS = 0; // Минимум часов
    private static final int MAX_HOURS = 15; // ТВОЙ ВАРИАНТ 2 (было 16, стало 15)
    private static final int DAILY_NORM = 8; // Норма в день для переработки
    private static final int WEEKLY_NORM = 40; // Недельная норма

    public static int[] parseHours(String line) {
        // 1. Проверка на пустоту
        if (line == null || line.trim().isEmpty()) {
            throw new IllegalArgumentException("Ошибка: пустая строка.");
        }

        // 2. Разбиваем строку по точке с запятой
        String[] parts = line.split(";");

        // 3. Проверяем количество частей
        if (parts.length != EXPECTED_VALUES) {
            throw new IllegalArgumentException("Ошибка: ожидалось " + EXPECTED_VALUES + " значений, а получено " + parts.length);
        }

        int[] hours = new int[EXPECTED_VALUES];

        // 4. Проходим циклом по каждой части
        for (int i = 0; i < parts.length; i++) {
            try {
                // Превращаем текст в число (обрезаем пробелы по краям)
                int value = Integer.parseInt(parts[i].trim());

                // Проверяем диапазон (от 0 до 15
                if (value < MIN_HOURS || value > MAX_HOURS) {
                    // i + 1, потому что позиции считаются с 1, а не с 0
                    throw new IllegalArgumentException("Ошибка в позиции " + (i + 1) + ": значение " + value + " вне диапазона (" + MIN_HOURS + "-" + MAX_HOURS + ").");
                }
                hours[i] = value;

            } catch (NumberFormatException e) {
                // Сюда попадаем, если в строке буквы вместо цифр
                throw new IllegalArgumentException("Ошибка в позиции " + (i + 1) + ": '" + parts[i] + "' не является целым числом.");
            }
        }
        return hours;
    }
    public static int total(int[] hours) {
        int sum = 0;
        for (int hour : hours) {
            sum += hour;
        }
        return sum;
    }
    public static double average(int[] hours) {
        if (hours.length == 0) return 0.0;
        return (double) total(hours) / hours.length; // (double) заставляет Java делить с дробями
    }
    public static int overtimeDays(int[] hours) {
        int count = 0;
        for (int hour : hours) {
            if (hour > DAILY_NORM) { // строго больше 8
                count++;
            }
        }
        return count;
    }
    public static int weeklyOvertime(int[] hours) {
        int sum = total(hours);
        return Math.max(0, sum - WEEKLY_NORM);
    }
}
