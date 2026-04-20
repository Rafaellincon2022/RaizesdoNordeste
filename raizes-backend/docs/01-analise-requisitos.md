# Analise e Requisitos

## Escopo
API Back-end para rede Raizes do Nordeste, suportando APP, TOTEM, BALCAO, PICKUP e WEB.

## Requisitos Funcionais
- RF01 Cadastro e autenticacao de usuarios com perfis.
- RF02 Consulta de cardapio/produtos.
- RF03 Gestao de unidades.
- RF04 Controle de estoque por unidade (entrada/saida/consulta).
- RF05 Criacao de pedido com itens, validacao de estoque e `canalPedido`.
- RF06 Consulta de pedidos com filtro por canal e status.
- RF07 Atualizacao de status do pedido.
- RF08 Pagamento mock com resposta de aprovado/recusado.
- RF09 Fidelidade com saldo e resgate.
- RF10 Auditoria de acoes sensiveis.

## Requisitos Nao Funcionais
- RNF01 API REST com contratos documentados no Swagger.
- RNF02 Erro padronizado em JSON.
- RNF03 Seguranca com JWT + autorizacao por roles.
- RNF04 Senhas armazenadas com hash (BCrypt).
- RNF05 Persistencia relacional em MySQL com migrations Flyway.

## Priorizacao de MVP
- MVP principal: `pedido -> pagamento mock -> atualizacao de status`.
- Itens complementares no MVP: autenticacao JWT, erro padronizado, `canalPedido`, estoque por unidade.
