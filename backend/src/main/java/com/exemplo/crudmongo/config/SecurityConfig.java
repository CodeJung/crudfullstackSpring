package com.exemplo.crudmongo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true) // habilita @PreAuthorize
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // desativa CSRF para facilitar testes
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated() // todas as requisições precisam de login
            )
            .httpBasic(); // login via Basic Auth

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        // Usuário coordenador
        UserDetails coordenador = User.builder()
                .username("coordenador")
                .password(passwordEncoder.encode("123")) // senha codificada
                .roles("COORDENADOR") // ROLE_COORDENADOR
                .build();

        // Usuário aluno
        UserDetails aluno = User.builder()
                .username("aluno")
                .password(passwordEncoder.encode("123"))
                .roles("ALUNO") // ROLE_ALUNO
                .build();

        return new InMemoryUserDetailsManager(coordenador, aluno);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
