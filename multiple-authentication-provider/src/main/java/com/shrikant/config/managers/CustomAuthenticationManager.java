package com.shrikant.config.managers;

import com.shrikant.config.providers.ApiKeyAuthenticationProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@AllArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private final String key;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var provider = new ApiKeyAuthenticationProvider(key);
        if(provider.supports(authentication.getClass())){
            return provider.authenticate(authentication);
        }
        return authentication;
    }
}
