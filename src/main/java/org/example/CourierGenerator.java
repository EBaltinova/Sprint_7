package org.example;

import io.qameta.allure.Step;

public class CourierGenerator {
    @Step("Получение стандартного курьера")
    public static Courier getDefault() {
        return new Courier("IchKurasakisan", "password", "Testen"); // Стандартный курьер
    }

    @Step("Получение курьера для случая с одинаковым логином")
    public static Courier getDefaultWithSameLogin() {
        return new Courier("IchKurasakisan", "password3", "Testensay"); // Курьер для тестирования кейса с одинаковым логином
    }

    @Step("Получение курьера без необходимого поля")
    public static Courier getWithoutNecessaryField() {
        return new Courier("password2", "Badtest"); // Курьер для тестирования кейса с необязательными полями
    }

    @Step("Получение курьера")
    public static Courier getSpecificCourier() {
        return new Courier("Mrlogin", "123456789", "Tester"); // Курьер для тестирования логина, его во время тестирования не удалять
    }

    @Step("Получение курьера с неправильным логином")
    public static Courier getSpecificCourierWithNotRightLogin() {
        return new Courier("Mslogin", "123456789", "Tester"); // Курьер для тестирования логина, с неправильным логином
    }

}
