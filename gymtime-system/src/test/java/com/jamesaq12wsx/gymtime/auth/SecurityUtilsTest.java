package com.jamesaq12wsx.gymtime.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamesaq12wsx.gymtime.security.Role;
import com.jamesaq12wsx.gymtime.util.SecurityUtils;
import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SecurityUtilsTest {

    SecurityUtils securityUtils;

    @Test
    public void testGetCurrentUsername(){

        String email = "jamesaq12wsx@gmail.com";

        securityUtils = mock(SecurityUtils.class);

        when(securityUtils.getCurrentUser()).thenReturn(new UserPrincipal(21l, email, "", Role.USER.getGrantedAuthorities()));
//        when(securityUtil.getCurrentUser()).thenReturn(new UserPrincipal(21l, email, "", Role.USER.getGrantedAuthorities()));

        assertEquals(email, securityUtils.getCurrentUser().getUsername());

        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonNode = mapper.convertValue(securityUtils.getCurrentUser(), JsonNode.class);

        assertEquals(Long.valueOf(21) , (Long)jsonNode.get("id").asLong(0));;
    }

}
