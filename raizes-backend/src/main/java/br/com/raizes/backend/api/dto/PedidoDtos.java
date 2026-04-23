package br.com.raizes.backend.api.dto;

import br.com.raizes.backend.domain.enums.CanalPedido;
import br.com.raizes.backend.domain.enums.PedidoStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class PedidoDtos {
    public record CriarPedidoRequest(
            @NotNull Long unidadeId,
            @NotNull Long clienteId,
            @NotNull CanalPedido canalPedido,
            @NotEmpty List<@Valid PedidoItemRequest> itens,
            @NotNull String formaPagamento
    ) {}

    public record PedidoItemRequest(
            @NotNull Long produtoId,
            @NotNull @Min(1) Integer quantidade
    ) {}

    public record PedidoItemResponse(
            Long produtoId,
            Integer quantidade,
            BigDecimal precoUnitario
    ) {}

    public record PedidoResponse(
            Long pedidoId,
            PedidoStatus status,
            CanalPedido canalPedido,
            BigDecimal total,
            String createdAt,
            List<PedidoItemResponse> itens
    ) {}

    public record AtualizarStatusRequest(@NotNull PedidoStatus status) {}
}
