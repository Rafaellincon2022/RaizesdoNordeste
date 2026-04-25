package br.com.raizes.backend.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

// Classe com DTOs relacionados ao sistema de fidelidade (pontos)
public class FidelidadeDtos {

    // DTO de resposta para consultar saldo de pontos do usuário
    public record SaldoResponse(

            // ID do usuário
            Long usuarioId,

            // Quantidade de pontos que o usuário possui
            Integer pontos,

            // Data da última atualização (provavelmente já formatada como texto)
            String atualizadoEm

    ) {}

    // DTO usado para solicitar resgate de pontos
    public record ResgateRequest(

            // Quantidade de pontos que o usuário quer resgatar (mínimo 1)
            @NotNull @Min(1) Integer pontos

    ) {}
}