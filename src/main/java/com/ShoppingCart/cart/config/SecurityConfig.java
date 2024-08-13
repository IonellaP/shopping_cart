package com.ShoppingCart.cart.config;

import com.ShoppingCart.cart.service.JwtAuthFilter;
import com.ShoppingCart.cart.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, UserDetailsServiceImpl userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/auth/login").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/api/auth/logout").hasRole("USER")
                .requestMatchers("/api/auth/admin/**").hasRole("ADMIN")
                .requestMatchers("/edit").hasRole("USER")
                .requestMatchers("/delete").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    Память обновлена
    To implement endpoint-based permissions for different users in a Spring Security project, you can follow these steps:

            1. Define Roles and Permissions
    You will define roles and permissions directly as endpoints in your Spring Security configuration.

            2. Create a Security Configuration
    You can configure the access rules in your SecurityConfig class.

    java
    Копировать код
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

    @Configuration
    @EnableWebSecurity
    public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/logout").hasAnyRole("USER", "ADMIN")
                            .requestMatchers("/edit").hasRole("USER")
                            .requestMatchers("/delete").hasRole("ADMIN")
                            .anyRequest().authenticated()
                    )
                    .formLogin()
                    .and()
                    .httpBasic();

            return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public UserDetailsService userDetailsService() {
            return username -> {
                if ("user".equals(username)) {
                    return org.springframework.security.core.userdetails.User
                            .withUsername(username)
                            .password(passwordEncoder().encode("password"))
                            .roles("USER")
                            .build();
                } else if ("admin".equals(username)) {
                    return org.springframework.security.core.userdetails.User
                            .withUsername(username)
                            .password(passwordEncoder().encode("password"))
                            .roles("ADMIN")
                            .build();
                } else {
                    throw new UsernameNotFoundException("User not found");
                }
            };
        }
    }
}