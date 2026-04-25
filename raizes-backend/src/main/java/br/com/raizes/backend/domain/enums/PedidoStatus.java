package br.com.raizes.backend.domain.enums;

// esse enum representa o status do pedido (em que etapa ele está)
// é tipo um fluxo do pedido
public enum PedidoStatus {

    // pedido foi criado mas ainda não foi pago
    AGUARDANDO_PAGAMENTO,

    // pagamento foi aprovado
    PAGO,

    // pedido está sendo preparado
    EM_PREPARO,

    // pedido já está pronto
    PRONTO,

    // pedido foi entregue ao cliente
    ENTREGUE,

    // pedido foi cancelado
    CANCELADO
}