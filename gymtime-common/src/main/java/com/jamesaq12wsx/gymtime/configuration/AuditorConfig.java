package com.jamesaq12wsx.gymtime.configuration;

import com.jamesaq12wsx.gymtime.util.SecurityUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 *
 */
@Component("auditorAware")
public class AuditorConfig implements AuditorAware<String> {

    /**
     * Return the operator identity data
     *
     * @return /
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        // Get the data
        return Optional.of(SecurityUtils.getCurrentUsername());
    }
}
