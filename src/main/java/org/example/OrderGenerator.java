package org.example;

import io.qameta.allure.Step;

public class OrderGenerator {
    @Step("Получение предпопределенного заказа")
    public static ForOrderData getDefaultOrder() {
        return new ForOrderData("Test", "Testov", "Тестовая д.34", "4", "+7 800 355 35 35", 5, "2023-01-20", "Test"); // Стандартный заказ
    }
}
