package com.shrikant.config.fitlers;

import com.shrikant.config.authentications.ApiKeyAuthentication;
import com.shrikant.config.managers.CustomAuthenticationManager;
import lombok.AllArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {

    private final String key;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        CustomAuthenticationManager cm = new CustomAuthenticationManager(key);

        var auth = new ApiKeyAuthentication(key);

        try{
            var a = cm.authenticate(auth);
            if(a.isAuthenticated()){
                SecurityContextHolder.getContext().setAuthentication(a);
                filterChain.doFilter(request,response);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }catch (AuthenticationException e){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
