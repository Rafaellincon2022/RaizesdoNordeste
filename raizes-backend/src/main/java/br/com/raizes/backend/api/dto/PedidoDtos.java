package br.com.raizes.backend.api.dto;

import br.com.raizes.backend.domain.enums.CanalPedido;
import br.com.raizes.backend.domain.enums.PedidoStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

// Classe com DTOs relacionados aos pedidos
public class PedidoDtos {

    // DTO usado para criar um novo pedido
    public record CriarPedidoRequest(

            // ID da unidade (loja/filial)
            @NotNull Long unidadeId,

            // ID do cliente que está fazendo o pedido
            @NotNull Long clienteId,

            // Canal do pedido (ex: APP, LOJA, DELIVERY)
            @NotNull CanalPedido canalPedido,

            // Lista de itens do pedido (não pode estar vazia)
            // @Valid faz validar cada item dentro da lista também
            @NotEmpty List<@Valid PedidoItemRequest> itens,

            // Forma de pagamento (ex: PIX, CARTAO)
            @NotNull String formaPagamento

    ) {}

    // DTO que representa cada item enviado no pedido
    public record PedidoItemRequest(

            // ID do produto
            @NotNull Long produtoId,

            // Quantidade do produto (mínimo 1)
            @NotNull @Min(1) Integer quantidade

    ) {}

    // DTO de resposta para cada item do pedido
    public record PedidoItemResponse(

            // ID do produto
            Long produtoId,

            // Quantidade comprada
            Integer quantidade,

            // Preço unitário do produto no momento da compra
            BigDecimal precoUnitario

    ) {}

    // DTO de resposta com os dados do pedido
    public record PedidoResponse(

            // ID do pedido
            Long pedidoId,

            // Status do pedido (ex: CRIADO, PAGO, CANCELADO)
            PedidoStatus status,

            // Canal por onde o pedido foi feito
            CanalPedido canalPedido,

            // Valor total do pedido
            BigDecimal total,

            // Data de criação do pedido (provavelmente já formatada)
            String createdAt,

            // Lista de itens do pedido
            List<PedidoItemResponse> itens

    ) {}

    // DTO usado para atualizar o status do pedido
    public record AtualizarStatusRequest(

            // Novo status do pedido
            @NotNull PedidoStatus status

    ) {}
}