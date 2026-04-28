# Raizes do Nordeste - Back-end

API REST em Java (Spring Boot + Maven) para o estudo de caso da rede **Raizes do Nordeste**, com foco em:
- multicanalidade (`canalPedido`)
- fluxo critico `pedido -> pagamento mock -> status`
- seguranca com JWT e perfis
- estoque por unidade
- fidelidade e auditoria basica

## Tecnologias
- Java 17
- Spring Boot 3
- Spring Web, Validation, Data JPA, Security
- Flyway
- MySQL 8
- Swagger/OpenAPI (`/swagger`)

## Arquitetura por camadas
- `domain`: entidades e enums de dominio
- `application`: servicos/casos de uso
- `infrastructure`: repositorios, seguranca e seed
- `api`: controllers, DTOs e tratamento de erro

## Como executar
1. Suba o banco MySQL:
   ```bash
   docker compose up -d
   ```
2. Rode a API:
   ```bash
   mvn spring-boot:run
   ```
3. Acesse:
   - Swagger: `http://localhost:8080/swagger`

Para parar:
```bash
docker compose down
```

## Variaveis de ambiente
Base no arquivo `.env.example`:
- `APP_PORT=8080`
- `RAIZES_DB_URL=jdbc:mysql://localhost:3307/raizesdb?...`
- `RAIZES_DB_USER=raizes`
- `RAIZES_DB_PASSWORD=raizes123`
- `JWT_SECRET=...`

## Seed inicial
Ao subir a API, sao criados dados iniciais para teste:
- `admin@raizes.com / Senha@123` (role `ADMIN`)
- `cliente@raizes.com / Senha@123` (role `CLIENTE`)
- 1 unidade
- 2 produtos
- estoque inicial por produto

## Endpoints implementados
### Auth
- `POST /auth/register`
- `POST /auth/login`

### Unidades
- `GET /unidades`

### Produtos
- `GET /produtos`
- `POST /produtos` (ADMIN, GERENTE)
- `PUT /produtos/{id}` (ADMIN, GERENTE)

### Estoque
- `GET /estoque/unidades/{unidadeId}`
- `POST /estoque/entrada` (ADMIN, GERENTE, ATENDENTE)
- `POST /estoque/saida` (ADMIN, GERENTE, ATENDENTE)

### Pedidos
- `POST /pedidos` (CLIENTE, ATENDENTE, ADMIN, GERENTE)
- `GET /pedidos?canalPedido=APP&status=AGUARDANDO_PAGAMENTO`
- `PATCH /pedidos/{pedidoId}/status` (COZINHA, ATENDENTE, ADMIN, GERENTE)

### Pagamentos (mock)
- `POST /pagamentos/pedidos/{pedidoId}/mock` (ATENDENTE, ADMIN, GERENTE)

### Fidelidade
- `GET /fidelidade/usuarios/{usuarioId}/saldo`
- `POST /fidelidade/usuarios/{usuarioId}/resgates`

## Regras de negocio implementadas
- `canalPedido` obrigatorio no pedido (APP, TOTEM, BALCAO, PICKUP, WEB)
- validacao de estoque na criacao do pedido
- baixa de estoque no pedido
- pagamento mock aprovado: pedido vai para `PAGO`
- pagamento mock recusado: pedido vai para `CANCELADO`
- filtro de pedidos por `canalPedido` e `status`
- pontos de fidelidade acumulados por valor inteiro do pedido
- resgate de pontos com validacao de saldo
- auditoria basica de criacao de pedido, mudanca de status e pagamento mock

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

## Colecao Postman
Arquivo: `postman/raizes-backend.postman_collection.json`

## Plano de testes (10 cenarios)
- T01 Login valido (admin) -> `200`
- T02 Login valido (cliente) -> `200`
- T03 Criar pedido com itens validos -> `201`
- T04 Pagamento mock aprovado -> `200` e pedido atualizado
- T05 Listar pedidos por canal -> `200`
- T06 Consultar saldo de fidelidade -> `200`
- T07 Acesso sem token em `/pedidos` -> `401`
- T08 Criar produto com perfil CLIENTE -> `403`
- T09 Saida de estoque acima do disponivel -> `409`
- T10 Criar pedido sem `canalPedido` -> `422`

## Documentacao complementar da entrega
- `docs/01-analise-requisitos.md`
- `docs/02-modelagem-arquitetura.md`
- `docs/03-contrato-api.md`
- `docs/04-plano-testes.md`
