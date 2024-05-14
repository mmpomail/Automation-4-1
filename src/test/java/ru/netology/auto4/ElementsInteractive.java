package ru.netology.auto4;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class ElementsInteractive {

    private String generate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void testUsingInteractiveElements() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Са");
        $$(".menu-item__control").findBy(text("Санкт-Петербург")).click();
        String planingDate = generate(7, "dd.MM.yyyy");
        $("[data-test-id='date']").click();
        if (!generate(3, "MM").equals(generate(7, "MM"))) {
            $$(".calendar__arrow.calendar__arrow_direction_right").last().click();
        }
        String planingDay = generate(7, "d");
        $$(".calendar__day").findBy(text(planingDay)).click();
        $("[name='name']").setValue("Петр Румянцев-Задунайский");
        $("[name='phone']").setValue("+79211234567");
        $("[data-test-id='agreement']").click();
        $(".button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + planingDate));
    }
}