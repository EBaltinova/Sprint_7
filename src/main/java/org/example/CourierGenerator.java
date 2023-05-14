package org.example;

public class CourierGenerator {

    public static Courier getDefault() {
        return new Courier("IchKurasakisan", "password", "Testen"); // Стандартный курьер
    }
    public static Courier getDefaultWithSameLogin() {
        return new Courier("IchKurasakisan", "password3", "Testensay"); // Курьер для тестирования кейса с одинаковым логином
    }

    public static Courier getWithoutNecessaryField() {
        return new Courier("password2", "Badtest"); // Курьер для тестирования кейса с необязательными полями
    }

    public static Courier getSpecificCourier() {
        return new Courier("Mrlogin", "123456789", "Tester"); // Курьер для тестирования логина, его во время тестирования не удалять
    }

    public static Courier getSpecificCourierWithNotRightLogin() {
        return new Courier("Mslogin", "123456789", "Tester"); // Курьер для тестирования логина, с неправильным логином
    }

}
