package com.jamesaq12wsx.gymtime;

import com.jamesaq12wsx.gymtime.validator.EmailValidator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EmailValidTest {

    @Test
    public void testValidEmail(){
        EmailValidator validator = new EmailValidator();

        assertThat(validator.test("asdf@gmail.com")).isTrue();
        assertThat(validator.test("asdf@gmail")).isFalse();
    }

}
