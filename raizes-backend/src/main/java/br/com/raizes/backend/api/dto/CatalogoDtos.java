package br.com.raizes.backend.api.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

// Classe que agrupa DTOs relacionados ao catálogo (produtos e unidades)
public class CatalogoDtos {

    // DTO usado para criar ou atualizar um produto
    public record ProdutoRequest(

            // Nome do produto (não pode ser vazio)
            @NotBlank String nome,

            // Preço do produto (não pode ser nulo e deve ser maior que 0.01)
            @NotNull @DecimalMin("0.01") BigDecimal preco,

            // Indica se o produto está ativo (true ou false)
            boolean ativo

    ) {}

    // DTO de resposta com dados do produto
    public record ProdutoResponse(

            // ID do produto no banco
            Long id,

            // Nome do produto
            String nome,

            // Preço do produto
            BigDecimal preco,

            // Indica se está ativo
            boolean ativo

    ) {}

    // DTO de resposta para unidade (ex: loja/filial)
    public record UnidadeResponse(

            // ID da unidade
            Long id,

            // Nome da unidade
            String nome,

            // Cidade onde a unidade está
            String cidade,

            // Indica se a unidade está ativa
            boolean ativa

    ) {}
}