package com.exemplo.crudmongo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true) // 🔥 habilita o @PreAuthorize
public class SecurityConfig {

    // Configura as regras de acesso HTTP
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // desativa CSRF (ok para API REST)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/cursos/**").authenticated() // protege todos os cursos
                .anyRequest().permitAll() // libera outras rotas
            )
            .httpBasic(httpBasic -> {}); // habilita login via navegador ou Postman
        return http.build();
    }

    // Usuários em memória
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails coordenador = User.withUsername("coordenador")
                .password("1234") // senha simples
                .roles("COORDENADOR") // ROLE_COORDENADOR
                .build();

        UserDetails aluno = User.withUsername("aluno")
                .password("1234") // senha simples
                .roles("ALUNO") // ROLE_ALUNO
                .build();

        return new InMemoryUserDetailsManager(coordenador, aluno);
    }

    // Password encoder simples (não recomendado para produção)
    @Bean
    @SuppressWarnings("deprecation")
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}
