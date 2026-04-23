package br.com.raizes.backend.api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CatalogoDtos {
    public record ProdutoRequest(
            @NotBlank String nome,
            @NotNull @DecimalMin("0.01") BigDecimal preco,
            boolean ativo
    ) {}

    public record ProdutoResponse(Long id, String nome, BigDecimal preco, boolean ativo) {}

    public record UnidadeResponse(Long id, String nome, String cidade, boolean ativa) {}
}
