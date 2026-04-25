package br.com.raizes.backend.domain.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

// essa classe representa um item dentro de um pedido
@Entity

// nome da tabela no banco
@Table(name = "pedido_itens")
public class PedidoItem {

    // id da tabela (chave primária)
    @Id

    // o banco gera automaticamente
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // relacionamento com pedido
    // vários itens pertencem a um único pedido
    @ManyToOne(optional = false)

    // coluna no banco
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    // relacionamento com produto
    // vários itens podem ser do mesmo produto
    @ManyToOne(optional = false)

    @JoinColumn(name = "produto_id")
    private Produto produto;

    // quantidade desse produto no pedido
    // não pode ser nulo
    @Column(nullable = false)
    private Integer quantidade;

    // preço unitário do produto no momento da compra
    // isso é importante pra manter histórico correto
    @Column(name = "preco_unitario", nullable = false, precision = 12, scale = 2)
    private BigDecimal precoUnitario;

    // getter do id
    public Long getId() { 
        return id; 
    }

    // pega o pedido
    public Pedido getPedido() { 
        return pedido; 
    }

    // define o pedido
    public void setPedido(Pedido pedido) { 
        this.pedido = pedido; 
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

    // pega o preço unitário
    public BigDecimal getPrecoUnitario() { 
        return precoUnitario; 
    }

    // define o preço unitário
    public void setPrecoUnitario(BigDecimal precoUnitario) { 
        this.precoUnitario = precoUnitario; 
    }
}