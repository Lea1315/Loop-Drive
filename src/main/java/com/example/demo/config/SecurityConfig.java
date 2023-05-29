package com.example.demo.config;

import com.example.demo.service.UserInfoUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    public UserInfoUserDetailService userDetailsService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
       /* return httpSecurity.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/users").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/groups").permitAll()
                .and().formLogin().permitAll()
                .and().build(); */
        return httpSecurity.authorizeHttpRequests().anyRequest().authenticated()
                .and()
                .httpBasic()
                .and().build();
    }

     @Bean
     public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }




}
