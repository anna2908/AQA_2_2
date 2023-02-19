package ru.netology.web;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CardTest {
    String data = LocalDate.now().plusDays(10).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    String deleteString = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;


    @Test
    void shouldBooking() {
        open("http://localhost:9999/");
        $("[data-test-id=\"city\"] input").setValue("Смоленск");
        $("[data-test-id=\"date\"] input").sendKeys(deleteString);
        $("[data-test-id=\"date\"] input").setValue(data);
        $("[data-test-id=\"name\"] input").setValue("Белошкурник Анна");
        $("[data-test-id=\"phone\"] input").setValue("+79107651717");
        $("[data-test-id=\"agreement\"]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id=\"notification\"]").should(visible, Duration.ofSeconds(15));
    }

    @Test
    void allFieldsEmpty() {
        open("http://localhost:9999/");
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@data-test-id=\"city\"]//span[@class=\"input__sub\"]").should(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void uncorrectedCity() {
        open("http://localhost:9999/");
        String data = LocalDate.now().plusDays(10).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String deleteString = Keys.chord(Keys.CONTROL, "a") + Keys.DELETE;
        $("[data-test-id=\"city\"] input").setValue("Вязьма");
        $("[data-test-id=\"date\"] input").sendKeys(deleteString);
        $("[data-test-id=\"date\"] input").setValue(data);
        $("[data-test-id=\"name\"] input").setValue("Белошкурник Анна");
        $("[data-test-id=\"phone\"] input").setValue("+79107651717");
        $("[data-test-id=\"agreement\"]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@data-test-id=\"city\"]//span[@class=\"input__sub\"]").should(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    void uncorrectedDate() {
        open("http://localhost:9999/");
        $("[data-test-id=\"city\"] input").setValue("Смоленск");
        $("[data-test-id=\"date\"] input").sendKeys(deleteString);
        $("[data-test-id=\"date\"] input").setValue("10.10.2010");
        $("[data-test-id=\"name\"] input").setValue("Белошкурник Анна");
        $("[data-test-id=\"phone\"] input").setValue("+79107651717");
        $("[data-test-id=\"agreement\"]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@data-test-id=\"date\"]//span[@class=\"input__sub\"]").should(exactText("Заказ на выбранную дату невозможен"));
    }

    @Test
    void uncorrectedNameWithLatin() {
        open("http://localhost:9999/");
        $("[data-test-id=\"city\"] input").setValue("Смоленск");
        $("[data-test-id=\"date\"] input").sendKeys(deleteString);
        $("[data-test-id=\"date\"] input").setValue(data);
        $("[data-test-id=\"name\"] input").setValue("Beloshkurnik Anna");
        $("[data-test-id=\"phone\"] input").setValue("+79107651717");
        $("[data-test-id=\"agreement\"]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@data-test-id=\"name\"]//span[@class=\"input__sub\"]").should(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void uncorrectedNameWithNumber() {
        open("http://localhost:9999/");
        $("[data-test-id=\"city\"] input").setValue("Смоленск");
        $("[data-test-id=\"date\"] input").sendKeys(deleteString);
        $("[data-test-id=\"date\"] input").setValue(data);
        $("[data-test-id=\"name\"] input").setValue("Анна123456");
        $("[data-test-id=\"phone\"] input").setValue("+79107651717");
        $("[data-test-id=\"agreement\"]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@data-test-id=\"name\"]//span[@class=\"input__sub\"]").should(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void uncorrectedNameWithSpecialSymbol() {
        open("http://localhost:9999/");
        $("[data-test-id=\"city\"] input").setValue("Смоленск");
        $("[data-test-id=\"date\"] input").sendKeys(deleteString);
        $("[data-test-id=\"date\"] input").setValue(data);
        $("[data-test-id=\"name\"] input").setValue("Анна!#");
        $("[data-test-id=\"phone\"] input").setValue("+79107651717");
        $("[data-test-id=\"agreement\"]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@data-test-id=\"name\"]//span[@class=\"input__sub\"]").should(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void uncorrectedPhone() {
        open("http://localhost:9999/");
        $("[data-test-id=\"city\"] input").setValue("Смоленск");
        $("[data-test-id=\"date\"] input").sendKeys(deleteString);
        $("[data-test-id=\"date\"] input").setValue(data);
        $("[data-test-id=\"name\"] input").setValue("Белошкурник Анна");
        $("[data-test-id=\"phone\"] input").setValue("+7");
        $("[data-test-id=\"agreement\"]").click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//span[@data-test-id=\"phone\"]//span[@class=\"input__sub\"]").should(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void uncorrectedCheckBox() {
        open("http://localhost:9999/");
        $("[data-test-id=\"city\"] input").setValue("Смоленск");
        $("[data-test-id=\"date\"] input").sendKeys(deleteString);
        $("[data-test-id=\"date\"] input").setValue(data);
        $("[data-test-id=\"name\"] input").setValue("Белошкурник Анна");
        $("[data-test-id=\"phone\"] input").setValue("+79107651717");
        $x("//*[contains(text(), 'Забронировать')]").click();
        $x("//label[@data-test-id=\"agreement\"]//span[@class=\"checkbox__text\"]").should(cssValue("color", "rgba(255, 92, 92, 1)"));
    }
}
