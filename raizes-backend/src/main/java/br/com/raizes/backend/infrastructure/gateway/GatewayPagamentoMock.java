package br.com.raizes.backend.infrastructure.gateway;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class GatewayPagamentoMock {

    // Simula o retorno de uma API externa (Payload do Mock)
    public RespostaGateway processar(Double valor) {
        // Simula o delay de rede (meio segundo)
        try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }

        String transacaoId = UUID.randomUUID().toString();

        // Regra mock para forçar erro nos testes: se o valor terminar com .99, recusa.
        if (valor != null && String.valueOf(valor).endsWith(".99")) {
            return new RespostaGateway(transacaoId, "RECUSADO", "Saldo insuficiente no cartão.");
        }

        return new RespostaGateway(transacaoId, "APROVADO", "Pagamento processado com sucesso.");
    }

    public record RespostaGateway(String transacaoId, String status, String mensagem) {}
}