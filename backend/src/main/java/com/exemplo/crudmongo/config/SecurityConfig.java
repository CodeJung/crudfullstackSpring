package com.exemplo.crudmongo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
         http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
            .requestMatchers(
            "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/h2-console/**",
                "/error"
            ).permitAll()
            .requestMatchers(HttpMethod.GET, "/api/cursos/**")
                .hasAnyRole("ALUNO", "COORDENADOR")
            .requestMatchers(HttpMethod.POST, "/api/cursos/**")
                .hasRole("COORDENADOR")
            .requestMatchers(HttpMethod.PUT, "/api/cursos/**")
                .hasRole("COORDENADOR")
            .requestMatchers(HttpMethod.DELETE, "/api/cursos/**")
                .hasRole("COORDENADOR")
            .anyRequest().authenticated()
        )
        .httpBasic(httpBasic -> {})
        .headers(headers -> headers.frameOptions(frame -> frame.disable()));

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails coordenador = User.builder()
            .username("coordenador")
            .password(passwordEncoder.encode("coord123"))
            .roles("COORDENADOR")
            .build();

        UserDetails aluno = User.builder()
            .username("aluno")
            .password(passwordEncoder.encode("alun123"))
            .roles("ALUNO")
            .build();

        return new InMemoryUserDetailsManager(coordenador, aluno);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
