package br.com.raizes.backend.api.dto;

import br.com.raizes.backend.domain.enums.PagamentoStatus;
import jakarta.validation.constraints.NotNull;

// Classe com DTOs relacionados ao pagamento
public class PagamentoDtos {

    // DTO usado para processar um pagamento
    public record ProcessarPagamentoRequest(

            // Indica se o pagamento foi aprovado ou não (true ou false)
            @NotNull Boolean aprovado

    ) {}

    // DTO de resposta após o processamento do pagamento
    public record PagamentoResponse(

            // ID do pedido relacionado ao pagamento
            Long pedidoId,

            // Status do pagamento (ex: APROVADO, RECUSADO, etc)
            PagamentoStatus status,

            // Retorno do sistema de pagamento (mensagem ou detalhes)
            String payloadRetorno

    ) {}
}