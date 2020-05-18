package com.jamesaq12wsx.gymtime.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.InetAddress;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilsTest {

    String path = "src/main/resources";

    @Test
    public void testIpToCityAndCountry(){
        String ip = "75.142.57.90";

        try {
            String city = StringUtils.getCityInfo(ip);

            assertTrue(city.contains("Pasadena"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}