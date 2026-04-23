package br.com.raizes.backend.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class EstoqueDtos {
    public record MovimentoRequest(
            @NotNull Long unidadeId,
            @NotNull Long produtoId,
            @NotNull @Min(1) Integer quantidade
    ) {}

    public record EstoqueResponse(
            Long unidadeId,
            Long produtoId,
            String produtoNome,
            Integer quantidade
    ) {}
}
