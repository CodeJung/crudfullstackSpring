package com.exemplo.crudmongo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // Habilita o @PreAuthorize
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desabilita CSRF para APIs REST
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // API Stateless
            .authorizeHttpRequests(authz -> authz
                // Permite acesso ao Swagger UI (se você o tiver)
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                // Protege todos os outros endpoints de /api/cursos
                .requestMatchers("/api/cursos/**").authenticated() 
                .anyRequest().permitAll()
            )
            .httpBasic(Customizer.withDefaults()); // Usa autenticação HTTP Basic
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Cria usuários de teste em memória
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails coordenador = User.builder()
                .username("coordenador")
                .password(passwordEncoder().encode("senha123"))
                .roles("COORDENADOR")
                .build();

        UserDetails aluno = User.builder()
                .username("aluno")
                .password(passwordEncoder().encode("senha456"))
                .roles("ALUNO")
                .build();

        return new InMemoryUserDetailsManager(coordenador, aluno);
    }
}