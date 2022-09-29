package com.shrikant.config;

import com.shrikant.config.fitlers.ApiKeyFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.httpBasic()
                .and()
                .addFilterBefore(new ApiKeyFilter(), BasicAuthenticationFilter.class)
                //.and().authenticationManager() // we also create this by adding the bean in context
                //.and().authenticationProvider() // doesn't the override the default provider , it adds to the collection of AP
                .build();
    }
}
// HttpSecurity => it create filters, authentications manager , providers, etc, it build the whole architecture
// of the filter chain

// Http Basic Authentication is just username+password base64 encoded in request header