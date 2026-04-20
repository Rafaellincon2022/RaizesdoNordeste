package br.com.raizes.backend.api.dto;

import br.com.raizes.backend.domain.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AuthDtos {
    public record RegisterRequest(
            @NotBlank String nome,
            @NotBlank @Email String email,
            @NotBlank @Size(min = 6) String senha,
            @NotNull Role role,
            boolean consentimentoLgpd
    ) {}

    public record LoginRequest(
            @NotBlank @Email String email,
            @NotBlank String senha
    ) {}

    public record AuthResponse(
            String accessToken,
            String tokenType,
            long expiresIn,
            UserView user
    ) {}

    public record UserView(Long id, String nome, String email, Role role) {}
}
