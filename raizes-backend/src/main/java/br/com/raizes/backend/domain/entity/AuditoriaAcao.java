package br.com.raizes.backend.domain.entity;

import jakarta.persistence.*;
import java.time.Instant;

// essa classe representa uma tabela no banco
@Entity

// aqui estou definindo o nome da tabela no banco
@Table(name = "auditoria_acoes")
public class AuditoriaAcao {

    // id da tabela (chave primária)
    @Id

    // aqui o banco vai gerar automaticamente (auto incremento)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // email da pessoa que fez a ação
    // nullable = false significa que não pode ser nulo
    @Column(name = "actor_email", nullable = false)
    private String actorEmail;

    // tipo da ação (ex: criar, deletar, atualizar)
    @Column(nullable = false)
    private String acao;

    // qual entidade foi afetada (ex: usuario, produto, etc)
    @Column(nullable = false)
    private String entidade;

    // id da entidade afetada (pode ser null)
    @Column(name = "entidade_id")
    private Long entidadeId;

    // detalhes adicionais da ação (limite de 500 caracteres)
    @Column(length = 500)
    private String detalhes;

    // data e hora que a ação foi criada
    @Column(name = "criado_em", nullable = false)
    private Instant criadoEm;

    // getter do id (não tem setter porque normalmente não mexe no id manualmente)
    public Long getId() {
        return id;
    }

    // pega o email de quem fez a ação
    public String getActorEmail() {
        return actorEmail;
    }

    // altera o email de quem fez a ação
    public void setActorEmail(String actorEmail) {
        this.actorEmail = actorEmail;
    }

    // pega o tipo da ação
    public String getAcao() {
        return acao;
    }

    // altera o tipo da ação
    public void setAcao(String acao) {
        this.acao = acao;
    }

    // pega a entidade
    public String getEntidade() {
        return entidade;
    }

    // altera a entidade
    public void setEntidade(String entidade) {
        this.entidade = entidade;
    }

    // pega o id da entidade
    public Long getEntidadeId() {
        return entidadeId;
    }

    // altera o id da entidade
    public void setEntidadeId(Long entidadeId) {
        this.entidadeId = entidadeId;
    }

    // pega os detalhes
    public String getDetalhes() {
        return detalhes;
    }

    // altera os detalhes
    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    // pega a data de criação
    public Instant getCriadoEm() {
        return criadoEm;
    }

    // altera a data de criação
    public void setCriadoEm(Instant criadoEm) {
        this.criadoEm = criadoEm;
    }
}