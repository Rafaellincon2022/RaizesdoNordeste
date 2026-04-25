package br.com.raizes.backend.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

// Classe com DTOs relacionados ao estoque
public class EstoqueDtos {

    // DTO usado para movimentar o estoque (entrada ou saída de produto)
    public record MovimentoRequest(

            // ID da unidade (ex: loja/filial)
            @NotNull Long unidadeId,

            // ID do produto
            @NotNull Long produtoId,

            // Quantidade que será movimentada (mínimo 1)
            @NotNull @Min(1) Integer quantidade

    ) {}

    // DTO de resposta com dados do estoque atual
    public record EstoqueResponse(

            // ID da unidade
            Long unidadeId,

            // ID do produto
            Long produtoId,

            // Nome do produto (facilita na exibição)
            String produtoNome,

            // Quantidade disponível em estoque
            Integer quantidade

    ) {}
}