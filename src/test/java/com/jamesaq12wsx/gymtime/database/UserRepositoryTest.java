package com.jamesaq12wsx.gymtime.database;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


//@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private ApplicationUserRepository userRepository;

    @Test
    public void testRepository()
    {
        assertThat(userRepository).isNotEqualTo(null);
    }

}
