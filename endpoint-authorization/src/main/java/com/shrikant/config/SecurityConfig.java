package com.shrikant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.httpBasic()
                .and()
                .authorizeRequests()
//                .anyRequest().authenticated() // endpoint level authorization
//                .anyRequest().permitAll()
//                .anyRequest().denyAll()
//                .anyRequest().hasAuthority("read")
//                .anyRequest().hasAnyAuthority("read","write")
//                .anyRequest().hasRole("ADMIN")
//                .anyRequest().hasAnyRole("READ","WRITE")
//                .anyRequest().access("isAuthenticated() and hasAuthority('read')") // SpEL => authorizing rules
                .mvcMatchers("/demo").hasAnyAuthority("read")
                .anyRequest().authenticated()
                .and().build();
    }
    // matcher method(anyRequest()) + authorization rule(authenticated())
    // 1. which matcher method I should use and how (anyRequest(), mvcMatchers(), antMatcher(), regexMatcher())
    // 2. how to apply different authorization rules

    @Bean
    public UserDetailsService uds(){
        var uds = new InMemoryUserDetailsManager();
        var user = User.withUsername("bill")
                .password(pe().encode("12345"))
                .authorities("read")
                .build();
        var user2 = User.withUsername("john")
                .password(pe().encode("12345"))
                .authorities("write")
                .build();
        uds.createUser(user);
        uds.createUser(user2);
        return uds;
    }

    @Bean
    public PasswordEncoder pe(){
        return new BCryptPasswordEncoder();
    }
}

// authentication always before athorization
// that's why when we pass wrong credentials with it will send the 401 error even though we have permitAll() at authorization
// we are asking for authentication by sending credentials(BasicAuth) and that's why done, even with permitAll(), permitAll() is at authorization level
// with NoAuth it skips the authentication and jumps to authorization filter

// 401 Unauthorized => when authentication fails
// 403 Forbidden => when authorization fails

// roles(ADMIN) is equivalent to authorities(ROLE_ADMIN)
// roles() will add ROLE_ at front, it's for convention