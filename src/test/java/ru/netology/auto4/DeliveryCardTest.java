package ru.netology.auto4;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryCardTest {

    DateSetting set = new DateSetting();

    @Test
    void testDeliveryCardWithRightData() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(set.generate(4));
        $("[name='name']").setValue("Петр Румянцев-Задунайский");
        $("[name='phone']").setValue("+79211234567");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='notification']").shouldHave(text("Встреча успешно забронирована на " + set.generate(4)), Duration.ofSeconds(15));
    }

    @Test
    void testDeliveryCardWithWrongCity() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Питер");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(set.generate(4));
        $("[name='name']").setValue("Петр Румянцев-Задунайский");
        $("[name='phone']").setValue("+79211234567");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(withText("Доставка в выбранный город недоступна")).shouldBe(visible);

    }

    @Test
    void testDeliveryCardWithEmptyCity() {
        open("http://localhost:9999");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(set.generate(4));
        $("[name='name']").setValue("Петр Румянцев-Задунайский");
        $("[name='phone']").setValue("+79211234567");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='city'] span.input__sub").shouldHave(exactText("Поле обязательно для заполнения")).shouldBe(visible);

    }

    @Test
    void testDeliveryCardWithWrongDate() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue("17.04.2003");
        $("[name='name']").setValue("Петр Румянцев-Задунайский");
        $("[name='phone']").setValue("+79211234567");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='date'] span.input__sub").shouldHave(exactText("Заказ на выбранную дату невозможен")).shouldBe(visible);
    }

    @Test
    void testDeliveryCardWithWrongName() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(set.generate(4));
        $("[name='name']").setValue("Petr Rumyantzev-Zadunaisky");
        $("[name='phone']").setValue("+79211234567");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(withText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.")).shouldBe(visible);

    }

    @Test
    void testDeliveryCardWithNoName() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(set.generate(4));
        $("[name='phone']").setValue("+79211234567");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $("[data-test-id='name'] span.input__sub").shouldHave(exactText("Поле обязательно для заполнения")).shouldBe(visible);

    }

    @Test
    void testDeliveryCardWithPhoneNumberLowerThanStandart() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(set.generate(4));
        $("[name='name']").setValue("Петр Румянцев-Задунайский");
        $("[name='phone']").setValue("+7921123456");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(withText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(visible);
    }

    @Test
    void testDeliveryCardWithPhoneNumberBiggerThanStandart() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(set.generate(4));
        $("[name='name']").setValue("Петр Румянцев-Задунайский");
        $("[name='phone']").setValue("+792112345678");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(withText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(visible);
    }

    @Test
    void testDeliveryCardWithNoAgreement() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='date'] input").setValue(set.generate(4));
        $("[name='name']").setValue("Петр Румянцев-Задунайский");
        $("[name='phone']").setValue("+79211234567");
        $("[data-test-id='agreement'] input_invalid");
        $(".button").click();
        $(".checkbox__text").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных")).shouldBe(visible);
    }

}
