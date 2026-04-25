package br.com.raizes.backend.domain.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

// essa classe representa um produto do sistema
@Entity

// nome da tabela no banco
@Table(name = "produtos")
public class Produto {

    // id do produto (chave primária)
    @Id

    // o banco gera automaticamente
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // nome do produto
    // não pode ser nulo
    @Column(nullable = false)
    private String nome;

    // preço do produto
    // usa BigDecimal porque é valor monetário
    // precision e scale controlam as casas decimais
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal preco;

    // indica se o produto está ativo ou não
    // true = ativo (pode vender)
    // false = inativo (não aparece ou não pode vender)
    @Column(nullable = false)
    private boolean ativo;

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

    // pega o preço
    public BigDecimal getPreco() { 
        return preco; 
    }

    // define o preço
    public void setPreco(BigDecimal preco) { 
        this.preco = preco; 
    }

    // verifica se está ativo
    public boolean isAtivo() { 
        return ativo; 
    }

    // define se está ativo
    public void setAtivo(boolean ativo) { 
        this.ativo = ativo; 
    }
}