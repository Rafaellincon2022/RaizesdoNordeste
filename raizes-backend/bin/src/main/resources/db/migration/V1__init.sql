create table usuarios (
    id bigint auto_increment primary key,
    nome varchar(120) not null,
    email varchar(160) not null unique,
    senha_hash varchar(255) not null,
    role varchar(30) not null,
    consentimento_lgpd boolean not null
);

create table unidades (
    id bigint auto_increment primary key,
    nome varchar(120) not null,
    cidade varchar(120) not null,
    ativa boolean not null
);

create table produtos (
    id bigint auto_increment primary key,
    nome varchar(120) not null,
    preco decimal(12,2) not null,
    ativo boolean not null
);

create table estoques (
    id bigint auto_increment primary key,
    unidade_id bigint not null,
    produto_id bigint not null,
    quantidade integer not null,
    unique(unidade_id, produto_id),
    constraint fk_estoques_unidades foreign key (unidade_id) references unidades(id),
    constraint fk_estoques_produtos foreign key (produto_id) references produtos(id)
);

create table pedidos (
    id bigint auto_increment primary key,
    cliente_id bigint not null,
    unidade_id bigint not null,
    canal_pedido varchar(20) not null,
    status varchar(30) not null,
    total decimal(12,2) not null,
    criado_em timestamp not null,
    constraint fk_pedidos_clientes foreign key (cliente_id) references usuarios(id),
    constraint fk_pedidos_unidades foreign key (unidade_id) references unidades(id)
);

create table pedido_itens (
    id bigint auto_increment primary key,
    pedido_id bigint not null,
    produto_id bigint not null,
    quantidade integer not null,
    preco_unitario decimal(12,2) not null,
    constraint fk_itens_pedido foreign key (pedido_id) references pedidos(id),
    constraint fk_itens_produto foreign key (produto_id) references produtos(id)
);

create table pagamentos (
    id bigint auto_increment primary key,
    pedido_id bigint not null unique,
    status varchar(20) not null,
    payload_retorno varchar(1200),
    registrado_em timestamp not null,
    constraint fk_pagamentos_pedido foreign key (pedido_id) references pedidos(id)
);

create table fidelidade_saldos (
    id bigint auto_increment primary key,
    usuario_id bigint not null unique,
    pontos integer not null default 0,
    atualizado_em timestamp not null,
    constraint fk_fidelidade_usuario foreign key (usuario_id) references usuarios(id)
);

create table auditoria_acoes (
    id bigint auto_increment primary key,
    actor_email varchar(160) not null,
    acao varchar(100) not null,
    entidade varchar(100) not null,
    entidade_id bigint,
    detalhes varchar(500),
    criado_em timestamp not null
);
