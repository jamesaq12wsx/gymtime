package com.jamesaq12wsx.gymtime.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jamesaq12wsx.gymtime.exception.ApiRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.*;

/**
 * @author James Lin
 * @date 2020-05-16
 */
public class SecurityUtil {

    public static Authentication getCurrentAuthentication(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * Get Current login User Data
     * @return UserDetails
     */
    public static UserDetails getCurrentUser() {
        final Authentication authentication = getCurrentAuthentication();
        if (authentication == null) {
            throw new ApiRequestException(HttpStatus.UNAUTHORIZED, "Current login expired");
        }
        if (authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            UserDetailsService userDetailsService = SpringContextHolder.getBean(UserDetailsService.class);
            return userDetailsService.loadUserByUsername(userDetails.getUsername());
        }
        throw new ApiRequestException(HttpStatus.UNAUTHORIZED, "Cannot find current login user");
    }

    /**
     * Get Current Username
     *
     * @return current username
     */
    public static String getCurrentUsername() {
        final Authentication authentication = getCurrentAuthentication();
        if (authentication == null) {
            throw new ApiRequestException(HttpStatus.UNAUTHORIZED, "Current Login Expire");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    /**
     * Get Current User Id
     * @return 系统用户ID
     */
    public static Long getCurrentUserId() {

        UserDetails userDetails = getCurrentUser();

        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonNode = mapper.convertValue(userDetails, JsonNode.class);

        return jsonNode.get("id").asLong();
    }

    /**
     * Get Current User Authority
     * @return /
     */
    public static Collection<? extends GrantedAuthority> getCurrentUserAuthorities(){
        UserDetails userDetails = getCurrentUser();

        return userDetails.getAuthorities();

    }

}
