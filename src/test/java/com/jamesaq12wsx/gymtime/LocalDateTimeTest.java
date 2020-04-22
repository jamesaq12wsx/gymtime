package com.jamesaq12wsx.gymtime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

public class LocalDateTimeTest {

    @Test
    public void testLocalDateTime(){
        LocalDateTime dateTime = LocalDateTime.now();

        System.out.println(dateTime.toString());
        System.out.println(ZonedDateTime.now());
        System.out.println(OffsetDateTime.now());
    }

}
