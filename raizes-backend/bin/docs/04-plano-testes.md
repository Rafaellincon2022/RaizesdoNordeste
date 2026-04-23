# Plano de Testes

## Cobertura minima
- Autenticacao/autorizacao: 200, 401, 403
- Validacao: 422
- Regra de negocio: 409 (estoque insuficiente)
- Fluxo critico: pedido + pagamento mock aprovado/recusado

## Cenarios
| ID | Endpoint | Tipo | Esperado |
|---|---|---|---|
| T01 | POST /auth/login (admin) | Positivo | 200 + token |
| T02 | POST /auth/login (cliente) | Positivo | 200 + token |
| T03 | POST /pedidos | Positivo | 201 + AGUARDANDO_PAGAMENTO |
| T04 | POST /pagamentos/pedidos/{id}/mock (aprovado) | Positivo | 200 + APROVADO |
| T05 | GET /pedidos?canalPedido=APP | Positivo | 200 |
| T06 | GET /fidelidade/usuarios/{id}/saldo | Positivo | 200 |
| T07 | GET /pedidos (sem token) | Negativo | 401 |
| T08 | POST /produtos (token cliente) | Negativo | 403 |
| T09 | POST /estoque/saida (quantidade alta) | Negativo | 409 |
| T10 | POST /pedidos sem canalPedido | Negativo | 422 |

## Evidencia executavel
- Colecao Postman: `postman/raizes-backend.postman_collection.json`
