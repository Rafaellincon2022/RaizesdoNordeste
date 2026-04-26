package br.com.raizes.backend.infrastructure.security;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

// Filtro que roda em toda requisição para validar o JWT
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    // Esse método roda automaticamente em toda request HTTP
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Pega o header Authorization
        String authHeader = request.getHeader("Authorization");

        // Se não tiver token ou estiver errado, segue normal sem autenticar
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Remove "Bearer " e pega só o token
        String token = authHeader.substring(7);

        try {
            // Decodifica o token e pega o email (subject)
            String email = jwtService.parseClaims(token).getSubject();

            // Se achou email e ainda não tem usuário logado
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // Busca usuário no banco
                var userDetails = userDetailsService.loadUserByUsername(email);

                // Cria autenticação do Spring
                var auth = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                // Adiciona detalhes da requisição
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Marca usuário como autenticado no sistema
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        } catch (JwtException ignored) {
            // Se token for inválido, limpa autenticação
            SecurityContextHolder.clearContext();
        }

        // Continua o fluxo da requisição
        filterChain.doFilter(request, response);
    }
}