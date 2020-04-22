package com.jamesaq12wsx.gymtime;

import com.jamesaq12wsx.gymtime.security.ApplicationSecurityConfig;
import com.jamesaq12wsx.gymtime.security.PasswordConfig;
import com.jamesaq12wsx.gymtime.validator.PasswordValidator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@Import(PasswordConfig.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PasswordConfigTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    PasswordConfig passwordConfig;

    @Test
    public void testConfig(){
        assertThat(passwordConfig.getMax()).isEqualTo(30);
        assertThat(passwordConfig.getMin()).isEqualTo(8);
    }

    @Test
    public void testPasswordValidate(){
        PasswordValidator validator = new PasswordValidator(passwordConfig);

        assertThat(validator.test("Asd!5951")).isTrue();
    }

}
