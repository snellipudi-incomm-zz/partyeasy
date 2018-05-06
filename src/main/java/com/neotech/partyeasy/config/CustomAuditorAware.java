package com.neotech.partyeasy.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class CustomAuditorAware  implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
        String loggedName = SecurityContextHolder.getContext().getAuthentication().getName();
        return loggedName;
    }
}
