package br.com.raizes.backend.infrastructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Classe principal de configuração de segurança
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    // Criptografia de senha
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Gerenciador de autenticação (login)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // Regras de segurança da API
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

            // Desativa proteção CSRF (comum em APIs JWT)
            .csrf(csrf -> csrf.disable())

            // Desativa frame options (para Swagger/h2 etc)
            .headers(headers -> headers.frameOptions(frame -> frame.disable()))

            // API não guarda sessão (usa JWT)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Regras de acesso
            .authorizeHttpRequests(auth -> auth

                // Rotas públicas (login + swagger)
                .requestMatchers(
                    "/auth/**",
                    "/v3/api-docs/**",
                    "/api-docs/**",
                    "/swagger-resources/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html",
                    "/webjars/**"
                ).permitAll()

                // Regras por rota + role
                .requestMatchers("/pedidos/**").hasAnyRole("CLIENTE", "ATENDENTE", "ADMIN", "GERENTE")
                .requestMatchers("/estoque/**").hasAnyRole("ADMIN", "GERENTE", "ATENDENTE")
                .requestMatchers("/produtos/**").hasAnyRole("ADMIN", "GERENTE")

                // Qualquer outra rota precisa estar logado
                .anyRequest().authenticated()
            )

            // Adiciona filtro JWT antes da autenticação padrão
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}