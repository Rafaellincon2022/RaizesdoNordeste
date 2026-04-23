# Modelagem e Arquitetura

## Arquitetura em camadas
- **Domain**: entidades e enums (`Pedido`, `Usuario`, `Estoque`, `Pagamento` etc.)
- **Application**: servicos de caso de uso (`PedidoService`, `PagamentoService`, `AuthService` etc.)
- **Infrastructure**: repositorios, configuracao de seguranca JWT, seed de dados
- **API**: controllers, DTOs e tratamento global de erro

## Entidades principais
- `Usuario` (role, consentimento LGPD, senha hash)
- `Unidade`
- `Produto`
- `Estoque` (por unidade/produto)
- `Pedido` e `PedidoItem` (com `canalPedido`)
- `Pagamento` (mock desacoplado)
- `FidelidadeSaldo`
- `AuditoriaAcao`

## DER simplificado (mermaid)
```mermaid
erDiagram
    USUARIOS ||--o{ PEDIDOS : "faz"
    UNIDADES ||--o{ PEDIDOS : "recebe"
    PEDIDOS ||--o{ PEDIDO_ITENS : "possui"
    PRODUTOS ||--o{ PEDIDO_ITENS : "compõe"
    UNIDADES ||--o{ ESTOQUES : "tem"
    PRODUTOS ||--o{ ESTOQUES : "controla"
    PEDIDOS ||--|| PAGAMENTOS : "gera"
    USUARIOS ||--|| FIDELIDADE_SALDOS : "possui"
```

## Fluxo critico (sequencia simplificada)
```mermaid
sequenceDiagram
    participant C as Cliente/App/Totem
    participant API as API Pedidos
    participant E as Estoque
    participant P as PagamentoMock

    C->>API: POST /pedidos (canalPedido, itens)
    API->>E: Validar e baixar estoque
    API-->>C: 201 Pedido AGUARDANDO_PAGAMENTO
    C->>P: POST /pagamentos/pedidos/{id}/mock
    P-->>API: APROVADO ou RECUSADO
    API-->>C: Pedido PAGO ou CANCELADO
```
