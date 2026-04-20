# Contrato da API

## Padrao de erro
```json
{
  "error": "CODIGO_ERRO",
  "message": "Mensagem legivel",
  "details": [],
  "timestamp": "2026-02-05T12:00:00Z",
  "path": "/rota"
}
```

## Exemplo - Login
- Metodo/Rota: `POST /auth/login`
- Auth: publico
- Request:
```json
{
  "email": "cliente@raizes.com",
  "senha": "Senha@123"
}
```

## Exemplo - Criar Pedido (fluxo critico)
- Metodo/Rota: `POST /pedidos`
- Auth: JWT (`CLIENTE|ATENDENTE|ADMIN|GERENTE`)
- Request:
```json
{
  "unidadeId": 1,
  "clienteId": 2,
  "canalPedido": "TOTEM",
  "itens": [
    { "produtoId": 1, "quantidade": 2 }
  ],
  "formaPagamento": "MOCK"
}
```
- Status esperados: `201`, `401`, `403`, `404`, `409`, `422`

## Exemplo - Listar pedidos por canal
- Metodo/Rota: `GET /pedidos?canalPedido=APP&status=AGUARDANDO_PAGAMENTO`
- Auth: JWT

## Exemplo - Pagamento mock
- Metodo/Rota: `POST /pagamentos/pedidos/{pedidoId}/mock`
- Auth: JWT (`ATENDENTE|ADMIN|GERENTE`)
- Request:
```json
{
  "aprovado": true
}
```
