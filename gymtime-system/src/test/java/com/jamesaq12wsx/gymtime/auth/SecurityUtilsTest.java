package com.jamesaq12wsx.gymtime.auth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamesaq12wsx.gymtime.security.Role;
import com.jamesaq12wsx.gymtime.util.SecurityUtil;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SecurityUtilTest {

    SecurityUtil securityUtil;

    @Test
    public void testGetCurrentUsername(){

        String email = "jamesaq12wsx@gmail.com";

        securityUtil = mock(SecurityUtil.class);

        when(securityUtil.getCurrentUser()).thenReturn(new UserPrincipal(21l, email, "", Role.USER.getGrantedAuthorities()));
//        when(securityUtil.getCurrentUser()).thenReturn(new UserPrincipal(21l, email, "", Role.USER.getGrantedAuthorities()));

        assertEquals(email, securityUtil.getCurrentUser().getUsername());

        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonNode = mapper.convertValue(securityUtil.getCurrentUser(), JsonNode.class);

        assertEquals(Long.valueOf(21) , (Long)jsonNode.get("id").asLong(0));;
    }

}
