package com.shrikant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
     //           .authorizeRequests(c -> c.anyRequest().authenticated()) // same as below config, using lambda
                .authorizeRequests()
//                .mvcMatchers("/test1").authenticated()
//                .mvcMatchers("/test2").hasAnyAuthority("read")
//                .mvcMatchers(HttpMethod.GET,"/demo/**").hasAuthority("read")
//                .anyRequest().authenticated()
                .antMatchers("/test/test1").authenticated() // this will work for "/test/test1" and not for "/test/test1/"
                // never use antMatchers is real world app
                .anyRequest().permitAll()
                .and().csrf().disable().build();
    }
    // matcher method(anyRequest()) + authorization rule(authenticated())
    // 1. which matcher method I should use and how (anyRequest(), mvcMatchers(), antMatcher(), regexMatcher())
    // 2. how to apply different authorization rules

    @Bean
    public UserDetailsService uds(){
        var uds = new InMemoryUserDetailsManager();

        var user1 = User.withUsername("bill")
                .password(pe().encode("12345"))
                .authorities("read") // reflect to GrantedAuthority interface
                .build();
        var user2 = User.withUsername("john")
                .password(pe().encode("12345"))
                .authorities("write")
                .build();
        uds.createUser(user1);
        uds.createUser(user2);
        return uds;
    }

    @Bean
    public PasswordEncoder pe(){
        return new BCryptPasswordEncoder();
    }
}
//   /demo/**  means anything after the /demo/ will match

// if we can't specify anything using ant expression in mvcMatchers() and antMatcher() then use regexMatchers()
