package br.com.raizes.backend.infrastructure.gateway;

import java.util.UUID;

import org.springframework.stereotype.Component;

// essa anotação faz o Spring reconhecer essa classe e permitir usar em outros lugares
@Component
public class GatewayPagamentoMock {

    // esse método simula o processamento de um pagamento
    public RespostaGateway processar(Double valor) {

        // aqui estou simulando um tempo de resposta de uma API externa (500ms)
        try { 
            Thread.sleep(500); 
        } catch (InterruptedException e) { 
            Thread.currentThread().interrupt(); 
        }

        // gera um id único para a transação
        String transacaoId = UUID.randomUUID().toString();

        // regra fake só para teste:
        // se o valor terminar com .99, o pagamento será recusado
        if (valor != null && String.valueOf(valor).endsWith(".99")) {
            return new RespostaGateway(
                transacaoId, 
                "RECUSADO", 
                "Saldo insuficiente no cartão."
            );
        }

        // caso contrário, o pagamento é aprovado
        return new RespostaGateway(
            transacaoId, 
            "APROVADO", 
            "Pagamento processado com sucesso."
        );
    }

    // esse record é tipo uma classe simples para guardar os dados da resposta
    public record RespostaGateway(
        String transacaoId, // id da transação
        String status, // status (APROVADO ou RECUSADO)
        String mensagem // mensagem explicando o resultado
    ) {}
}