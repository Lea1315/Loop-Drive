package com.example.demo.config;

import com.example.demo.repo.UserRepository;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfiguration {

    private final UserRepository userRepository;

    public SecurityConfig(UserRepository userRepo) {
        this.userRepository = userRepo;
    }


}
