package ru.netology.auto4;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateSetting {

    public String generate (int days) {


        LocalDate date = LocalDate.now();

        LocalDate day = date.plusDays(days);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        String text = day.format(formatter);

        return text;

    }

}
