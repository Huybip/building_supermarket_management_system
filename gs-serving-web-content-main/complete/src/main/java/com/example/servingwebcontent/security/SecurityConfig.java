package com.example.servingwebcontent.security;

import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
public class SecurityConfig {
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService userDetailsService;
    private final CorsConfigurationSource corsConfigurationSource;

    public SecurityConfig(JwtTokenUtil jwtTokenUtil, CustomUserDetailsService userDetailsService,
            CorsConfigurationSource corsConfigurationSource) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        JwtAuthenticationFilter jwtFilter = new JwtAuthenticationFilter(jwtTokenUtil, userDetailsService);

        http.csrf().disable()
                .cors().configurationSource(corsConfigurationSource).and()
                // allow session for form login (IF_REQUIRED) while JWT filter secures API endpoints
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).and()
        .authorizeHttpRequests(auth -> auth
            // allow static resources and public pages
            .requestMatchers("/", "/index.html", "/login", "/login.html", "/favicon.ico", "/css/**", "/js/**",
                "/images/**").permitAll()
            // web UI pages require authentication (after login)
            .requestMatchers("/products/**", "/categories/**", "/suppliers/**", "/customers/**", "/orders/**", "/invoices/**", "/users/**").authenticated()
            // allow auth endpoints and actuator
            .requestMatchers("/api/auth/**", "/actuator/**").permitAll()
            // allow H2 console in dev
            .requestMatchers("/h2-console/**").permitAll()
            // api endpoints require authentication
            .requestMatchers("/api/**").authenticated()
            .anyRequest().permitAll())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin(login -> login
                    .loginPage("/login")
                    .defaultSuccessUrl("/products", true)
                    .permitAll())
                .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .permitAll());

        // Allow frames for H2 console
        http.headers().frameOptions().disable();

        return http.build();
    }
}
