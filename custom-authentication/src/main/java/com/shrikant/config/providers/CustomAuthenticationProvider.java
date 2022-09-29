package com.shrikant.config.providers;

import com.shrikant.config.authentication.CustomAuthentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Value("${our.very.very.secret.key}")
    String key;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CustomAuthentication ca = (CustomAuthentication) authentication;

        String headerKey = ca.getKey();

        if(headerKey.equals(key)){
            return new CustomAuthentication(true,null);
        }
        throw new BadCredentialsException("Oh no");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthentication.class.equals(authentication);
        // telling authentication manager which type of authentication this provider supports
    }
}
