package ru.edu.pr02;

public class Main {
    public static void main(String[] args) {
        System.out.println("Мои собственные проверки:");

        // 1. Корректная строка (проверяем не сумму, а сам факт успешного парсинга)
        try {
            int[] hours = TimesheetAnalyzer.parseHours("9;8;7;10;8");
            System.out.println("Тест 1 (Корректная строка): PASS (Строка разобрана)");
        } catch (Exception e) {
            System.out.println("Тест 1: FAIL");
        }

        // 2. Пустая строка
        try {
            TimesheetAnalyzer.parseHours("");
            System.out.println("Тест 2 (Пустая строка): FAIL (Ошибка не была вызвана)");
        } catch (IllegalArgumentException e) {
            System.out.println("Тест 2 (Пустая строка): PASS (" + e.getMessage() + ")");
        }

        // 3. Значение 17 (превышает лимит)
        try {
            TimesheetAnalyzer.parseHours("8;17;8;8;8");
            System.out.println("Тест 3 (Значение 17): FAIL (Ошибка не была вызвана)");
        } catch (IllegalArgumentException e) {
            System.out.println("Тест 3 (Значение 17): PASS (" + e.getMessage() + ")");
        }

        // 4. Верхняя граница (для твоего варианта 2 это 15)
        try {
            TimesheetAnalyzer.parseHours("15;15;15;15;15");
            System.out.println("Тест 4 (Верхняя граница 15): PASS (Разрешено)");
        } catch (Exception e) {
            System.out.println("Тест 4: FAIL");
        }

        // 5. Выход за верхнюю границу (16)
        try {
            TimesheetAnalyzer.parseHours("16;8;8;8;8");
            System.out.println("Тест 5 (Значение 16): FAIL (Ошибка не была вызвана)");
        } catch (IllegalArgumentException e) {
            System.out.println("Тест 5 (Значение 16): PASS (" + e.getMessage() + ")");
        }

        // 6. Неверный формат (буквы)
        try {
            TimesheetAnalyzer.parseHours("8;abc;8;8;8");
            System.out.println("Тест 6 (Буквы): FAIL (Ошибка не была вызвана)");
        } catch (IllegalArgumentException e) {
            System.out.println("Тест 6 (Буквы): PASS (" + e.getMessage() + ")");
        }
    }
}