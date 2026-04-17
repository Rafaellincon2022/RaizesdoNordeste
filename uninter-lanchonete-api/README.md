# API Lanchonete UNINTER

Projeto em **Java 17 + Spring Boot + Maven** para simular backend de lanchonete estilo iFood simplificado.

## Funcionalidades implementadas

- Cadastro e login com JWT (`CLIENTE`, `ADMIN`, `ATENDENTE`)
- Cardapio/estoque por unidade
- Criacao de pedido com itens e `canalPedido` obrigatorio (`APP`, `TOTEM`, `BALCAO`, `WEB`)
- Fluxo principal: **pedido -> validacao estoque -> pagamento mock -> atualizacao de status**
- Cancelamento e atualizacao de status do pedido
- Fidelidade: pontos por compra aprovada (`total / 10`)
- Tratamento de erros padronizado (`400`, `401`, `403`, `404`, `409`)

## Endpoints principais

- `POST /auth/register`
- `POST /auth/login`
- `GET /produtos?unidadeId=1`
- `POST /pedidos`
- `GET /pedidos?canalPedido=APP`
- `PATCH /pedidos/{id}/status?status=EM_PREPARO`
- `PATCH /pedidos/{id}/cancelar`

## Credenciais seed

- Admin: `admin@uninter.com` / `123456`
- Cliente: `cliente@uninter.com` / `123456`

## Como executar

1. Tenha Java 17 e Maven instalados
2. Execute:

```bash
mvn spring-boot:run
```

3. H2 Console:
   - URL: `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:lanchonete`
   - User: `sa`
   - Password: vazio

## Testes

Foi criada a classe `PedidoControllerTest` com **10 cenarios**, cobrindo sucesso e erros (401/403/404/409).
