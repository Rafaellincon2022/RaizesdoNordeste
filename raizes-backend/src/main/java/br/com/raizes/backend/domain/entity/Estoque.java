package br.com.raizes.backend.domain.entity;

import jakarta.persistence.*;

// essa classe representa a tabela de estoque
@Entity

// aqui estou definindo o nome da tabela e uma regra única
// não pode repetir a combinação unidade + produto
@Table(
    name = "estoques",
    uniqueConstraints = @UniqueConstraint(columnNames = {"unidade_id", "produto_id"})
)
public class Estoque {

    // id da tabela (chave primária)
    @Id

    // o banco gera automaticamente
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // relacionamento com a tabela Unidade
    // muitos estoques podem ter a mesma unidade
    // optional = false -> não pode ser nulo
    @ManyToOne(optional = false)

    // nome da coluna no banco
    @JoinColumn(name = "unidade_id")
    private Unidade unidade;

    // relacionamento com a tabela Produto
    // muitos estoques podem ter o mesmo produto
    @ManyToOne(optional = false)

    // nome da coluna no banco
    @JoinColumn(name = "produto_id")
    private Produto produto;

    // quantidade disponível no estoque
    // não pode ser nulo
    @Column(nullable = false)
    private Integer quantidade;

    // getter do id
    public Long getId() { 
        return id; 
    }

    // pega a unidade
    public Unidade getUnidade() { 
        return unidade; 
    }

    // define a unidade
    public void setUnidade(Unidade unidade) { 
        this.unidade = unidade; 
    }

    // pega o produto
    public Produto getProduto() { 
        return produto; 
    }

    // define o produto
    public void setProduto(Produto produto) { 
        this.produto = produto; 
    }

    // pega a quantidade
    public Integer getQuantidade() { 
        return quantidade; 
    }

    // define a quantidade
    public void setQuantidade(Integer quantidade) { 
        this.quantidade = quantidade; 
    }
}