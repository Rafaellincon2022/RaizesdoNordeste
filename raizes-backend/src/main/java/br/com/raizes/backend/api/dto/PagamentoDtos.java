package br.com.raizes.backend.api.dto;

import br.com.raizes.backend.domain.enums.PagamentoStatus;
import jakarta.validation.constraints.NotNull;

public class PagamentoDtos {
    public record ProcessarPagamentoRequest(@NotNull Boolean aprovado) {}

    public record PagamentoResponse(
            Long pedidoId,
            PagamentoStatus status,
            String payloadRetorno
    ) {}
}
