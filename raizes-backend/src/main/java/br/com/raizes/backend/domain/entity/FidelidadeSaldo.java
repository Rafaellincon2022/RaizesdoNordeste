package br.com.raizes.backend.domain.entity;

import jakarta.persistence.*;
import java.time.Instant;

// essa classe representa o saldo de pontos de fidelidade de um usuário
@Entity

// nome da tabela no banco
@Table(name = "fidelidade_saldos")
public class FidelidadeSaldo {

    // id da tabela (chave primária)
    @Id

    // o banco gera automaticamente
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // relacionamento 1 pra 1 com usuario
    // cada usuario tem apenas um saldo
    // optional = false -> não pode ser nulo
    @OneToOne(optional = false)

    // coluna no banco que referencia o usuario
    // unique = true -> garante que não vai ter duplicado
    @JoinColumn(name = "usuario_id", unique = true)
    private Usuario usuario;

    // quantidade de pontos do usuário
    // não pode ser nulo
    @Column(nullable = false)
    private Integer pontos;

    // data da última atualização dos pontos
    // não pode ser nulo
    @Column(name = "atualizado_em", nullable = false)
    private Instant atualizadoEm;

    // getter do id
    public Long getId() {
        return id;
    }

    // pega o usuario
    public Usuario getUsuario() {
        return usuario;
    }

    // define o usuario
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    // pega os pontos
    public Integer getPontos() {
        return pontos;
    }

    // define os pontos
    public void setPontos(Integer pontos) {
        this.pontos = pontos;
    }

    // pega a data de atualização
    public Instant getAtualizadoEm() {
        return atualizadoEm;
    }

    // define a data de atualização
    public void setAtualizadoEm(Instant atualizadoEm) {
        this.atualizadoEm = atualizadoEm;
    }
}