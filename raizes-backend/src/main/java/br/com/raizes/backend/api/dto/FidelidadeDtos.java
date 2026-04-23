package br.com.raizes.backend.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class FidelidadeDtos {
    public record SaldoResponse(Long usuarioId, Integer pontos, String atualizadoEm) {}

    public record ResgateRequest(@NotNull @Min(1) Integer pontos) {}
}
