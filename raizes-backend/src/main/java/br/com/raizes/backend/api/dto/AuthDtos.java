package br.com.raizes.backend.api.dto;

import br.com.raizes.backend.domain.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// Classe que agrupa os DTOs de autenticação (login, cadastro, etc)
public class AuthDtos {

    // DTO usado para cadastro de usuário
    public record RegisterRequest(

            // Nome não pode ser vazio
            @NotBlank String nome,

            // Email não pode ser vazio e precisa ser válido
            @NotBlank @Email String email,

            // Senha não pode ser vazia e precisa ter no mínimo 6 caracteres
            @NotBlank @Size(min = 6) String senha,

            // Tipo de usuário (ex: ADMIN ou USER)
            @NotNull Role role,

            // Indica se o usuário aceitou os termos (LGPD)
            boolean consentimentoLgpd

    ) {}

    // DTO usado para login
    public record LoginRequest(

            // Email obrigatório e precisa ser válido
            @NotBlank @Email String email,

            // Senha obrigatória
            @NotBlank String senha

    ) {}

    // DTO de resposta após autenticação
    public record AuthResponse(

            // Token JWT que será usado nas próximas requisições
            String accessToken,

            // Tipo do token (geralmente "Bearer")
            String tokenType,

            // Tempo de expiração do token (em segundos)
            long expiresIn,

            // Dados do usuário logado
            UserView user

    ) {}

    // DTO com dados básicos do usuário (não expõe tudo)
    public record UserView(

            // ID do usuário no banco
            Long id,

            // Nome do usuário
            String nome,

            // Email do usuário
            String email,

            // Tipo de usuário (role)
            Role role

    ) {}
}