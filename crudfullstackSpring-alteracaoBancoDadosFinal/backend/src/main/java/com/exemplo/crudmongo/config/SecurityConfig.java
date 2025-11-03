package com.exemplo.crudmongo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)  // ✅ CRUCIAL - Habilita @PreAuthorize
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Desabilita CSRF (para testes com Postman/Insomnia)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.GET, "/api/cursos/**").hasAnyRole("ALUNO", "COORDENADOR")
                .requestMatchers("/api/cursos/**").hasRole("COORDENADOR")
                .requestMatchers("/pessoas/**").permitAll()  // Permite acesso sem autenticação
                .anyRequest().authenticated()
            )
            .httpBasic(basic -> {});  // Autenticação HTTP Basic
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Usuário com role COORDENADOR
        UserDetails coordenador = User.builder()
            .username("coordenador")
            .password(passwordEncoder().encode("123456"))
            .roles("COORDENADOR")
            .build();

        // Usuário com role ALUNO
        UserDetails aluno = User.builder()
            .username("aluno")
            .password(passwordEncoder().encode("123456"))
            .roles("ALUNO")
            .build();

        return new InMemoryUserDetailsManager(coordenador, aluno);
    }
}