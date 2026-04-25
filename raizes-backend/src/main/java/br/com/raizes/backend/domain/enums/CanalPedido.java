package br.com.raizes.backend.domain.enums;

// esse enum representa os canais onde o pedido pode ser feito
// enum é tipo uma lista fixa de valores
public enum CanalPedido {

    // pedido feito pelo aplicativo
    APP,

    // pedido feito em um totem (tipo autoatendimento)
    TOTEM,

    // pedido feito no balcão
    BALCAO,

    // pedido para retirada (pickup)
    PICKUP,

    // pedido feito pelo site (web)
    WEB
}