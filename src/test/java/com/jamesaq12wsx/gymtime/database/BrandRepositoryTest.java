package com.jamesaq12wsx.gymtime.database;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamesaq12wsx.gymtime.GymtimeApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BrandRepositoryTest {

    @Autowired
    private BrandDao brandDao;

    @Test
    public void testGetAllBrand() throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        System.out.println(mapper.writeValueAsString(brandDao.getAll()));

    }
}
