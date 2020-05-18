package com.jamesaq12wsx.gymtime.config;

import com.jamesaq12wsx.gymtime.util.SecurityUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author James Lin
 * @date 2020-05-16
 */
@Service(value = "gt")
public class PermissionConfig {

    public Boolean check(GrantedAuthority ...permissions){
        // Get Current User Authority
        List<String> gtPermissions = SecurityUtils.getCurrentUser().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        // Check Current User has method authority
        return gtPermissions.contains("ROLE_ADMIN") || Arrays.stream(permissions).anyMatch(gtPermissions::contains);
    }

}
