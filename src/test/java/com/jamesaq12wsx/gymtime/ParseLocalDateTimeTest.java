package com.jamesaq12wsx.gymtime;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class ParseLocalDateTimeTest {

    @Test
    public void parseTime(){
        LocalDateTime dateTime = LocalDateTime.parse("2020-03-27T16:33:21-07:00");
        assertThat(dateTime.getYear()).isEqualTo(2020);
    }

    @Test
    public void parseMonth(){
        LocalDate date = LocalDate.parse("2020-03-01");
        assertThat(date.getYear()).isEqualTo(2020);
    }

}
