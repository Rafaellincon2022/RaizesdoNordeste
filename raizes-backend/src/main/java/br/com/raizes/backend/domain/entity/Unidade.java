package br.com.raizes.backend.domain.entity;

import jakarta.persistence.*;

// essa classe representa uma unidade (tipo loja ou filial)
@Entity

// nome da tabela no banco
@Table(name = "unidades")
public class Unidade {

    // id da unidade (chave primária)
    @Id

    // o banco gera automaticamente
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nome da unidade
    // não pode ser nulo
    @Column(nullable = false)
    private String nome;

    // cidade onde a unidade está localizada
    // não pode ser nulo
    @Column(nullable = false)
    private String cidade;

    // indica se a unidade está ativa
    // true = ativa (funcionando)
    // false = inativa
    @Column(nullable = false)
    private boolean ativa;

    // getter do id
    public Long getId() { 
        return id; 
    }

    // pega o nome
    public String getNome() { 
        return nome; 
    }

    // define o nome
    public void setNome(String nome) { 
        this.nome = nome; 
    }

    // pega a cidade
    public String getCidade() { 
        return cidade; 
    }

    // define a cidade
    public void setCidade(String cidade) { 
        this.cidade = cidade; 
    }

    // verifica se está ativa
    public boolean isAtiva() { 
        return ativa; 
    }

    // define se está ativa
    public void setAtiva(boolean ativa) { 
        this.ativa = ativa; 
    }
}