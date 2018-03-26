package com.neotech.partyeasy.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;


public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {

    public SecurityInitializer() {
        super(SecurityConfig.class, HazelcastHttpClientConfig.class);
    }

}

