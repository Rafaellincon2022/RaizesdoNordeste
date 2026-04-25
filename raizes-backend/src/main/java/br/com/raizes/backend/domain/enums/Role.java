package br.com.raizes.backend.domain.enums;

// esse enum representa os tipos de usuário do sistema
// define o que cada usuário pode fazer
public enum Role {

    // administrador do sistema (acesso total)
    ADMIN,

    // gerente (controle geral da operação)
    GERENTE,

    // atendente (faz pedidos e atende clientes)
    ATENDENTE,

    // cozinha (prepara os pedidos)
    COZINHA,

    // cliente (usuário comum que faz pedidos)
    CLIENTE
}