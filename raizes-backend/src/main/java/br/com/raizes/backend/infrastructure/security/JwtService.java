package br.com.raizes.backend.infrastructure.security;

import br.com.raizes.backend.domain.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

// Serviço responsável por criar e validar JWT
@Service
public class JwtService {

    // Chave secreta usada para assinar o token
    private final SecretKey secretKey;

    // Tempo de vida do token em segundos
    private final long expirationSeconds;

    public JwtService(
            @Value("${JWT_SECRET:trocar_esta_chave_para_uma_chave_maior_em_producao_123456}") String secret,
            @Value("${JWT_EXPIRATION_SECONDS:3600}") long expirationSeconds
    ) {

        // Cria a chave secreta usada para assinar/verificar o token
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        this.expirationSeconds = expirationSeconds;
    }

    // Gera o token quando o usuário faz login
    public String generateToken(Usuario usuario) {

        Instant now = Instant.now();

        return Jwts.builder()

                // Email do usuário dentro do token
                .subject(usuario.getEmail())

                // Guarda a role (ADMIN, CLIENTE, etc)
                .claim("role", usuario.getRole().name())

                // Data de criação
                .issuedAt(Date.from(now))

                // Data de expiração
                .expiration(Date.from(now.plusSeconds(expirationSeconds)))

                // Assina o token (segurança)
                .signWith(secretKey)

                // Gera string final do token
                .compact();
    }

    // Lê e valida o token recebido
    public Claims parseClaims(String token) {

        return Jwts.parser()

                // Usa a chave secreta para validar assinatura
                .verifyWith(secretKey)

                .build()

                // Lê o token
                .parseSignedClaims(token)

                // Retorna os dados dentro do token
                .getPayload();
    }

    // Retorna tempo de expiração
    public long getExpirationSeconds() {
        return expirationSeconds;
    }
}