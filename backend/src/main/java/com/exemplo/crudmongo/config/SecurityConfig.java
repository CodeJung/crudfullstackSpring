
package com.exemplo.crudmongo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity // permite usar @PreAuthorize no controller
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // desativa CSRF para facilitar teste com Postman
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated() // exige login em todas as rotas
            )
            .httpBasic(); // autenticação básica (usuário/senha no header)

        return http.build();
    }

    // cria dois usuários em memória para teste
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails coordenador = User.withUsername("coordenador")
                .password("{noop}12345") // {noop} = sem criptografia
                .roles("COORDENADOR")
                .build();

        UserDetails aluno = User.withUsername("aluno")
                .password("{noop}12345")
                .roles("ALUNO")
                .build();

        return new InMemoryUserDetailsManager(coordenador, aluno);
    }
}
