package com.shrikant.config.providers;

import com.shrikant.config.authentications.ApiKeyAuthentication;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@AllArgsConstructor
public class ApiKeyAuthenticationProvider implements AuthenticationProvider {

    private final String apiKey;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ApiKeyAuthentication auth = (ApiKeyAuthentication) authentication;
        if(apiKey.equals(auth.getKey())){
            auth.setAuthenticated(true);
            return auth;
        }
        throw new BadCredentialsException(":(");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return ApiKeyAuthentication.class.equals(authentication);
    }
}
