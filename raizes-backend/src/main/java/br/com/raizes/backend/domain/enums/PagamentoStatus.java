package br.com.raizes.backend.domain.enums;

// esse enum representa o status do pagamento
// enum é uma lista fixa de valores possíveis
public enum PagamentoStatus {

    // pagamento foi aprovado (deu certo)
    APROVADO,

    // pagamento foi recusado (deu erro ou não autorizado)
    RECUSADO
}