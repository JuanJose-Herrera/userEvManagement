package com.epik.evm.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /*
        We can configure all about security from here, for the demo we need
        disable all the security added by spring boot
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().anyRequest().permitAll()
        .and()
            .cors()
                .and()
        .csrf()
        .disable();
    }

}
