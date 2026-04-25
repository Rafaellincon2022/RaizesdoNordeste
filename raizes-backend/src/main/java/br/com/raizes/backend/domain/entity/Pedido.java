package br.com.raizes.backend.domain.entity;

import br.com.raizes.backend.domain.enums.CanalPedido;
import br.com.raizes.backend.domain.enums.PedidoStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

// essa classe representa um pedido feito por um cliente
@Entity

// nome da tabela no banco
@Table(name = "pedidos")
public class Pedido {

    // id do pedido (chave primária)
    @Id

    // o banco gera automaticamente
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // cliente que fez o pedido
    // muitos pedidos podem ser do mesmo cliente
    @ManyToOne(optional = false)

    // coluna no banco
    @JoinColumn(name = "cliente_id")
    private Usuario cliente;

    // unidade onde o pedido foi feito
    // muitos pedidos podem ser da mesma unidade
    @ManyToOne(optional = false)

    @JoinColumn(name = "unidade_id")
    private Unidade unidade;

    // canal do pedido (app, site, loja, etc)
    // salvo como texto no banco
    @Enumerated(EnumType.STRING)

    @Column(name = "canal_pedido", nullable = false)
    private CanalPedido canalPedido;

    // status do pedido (pendente, finalizado, etc)
    @Enumerated(EnumType.STRING)

    @Column(nullable = false)
    private PedidoStatus status;

    // valor total do pedido
    // precision e scale são para valores monetários
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal total;

    // data de criação do pedido
    @Column(name = "criado_em", nullable = false)
    private Instant criadoEm;

    // lista de itens do pedido
    // um pedido pode ter vários itens
    @OneToMany(
        mappedBy = "pedido", // quem controla é o PedidoItem
        cascade = CascadeType.ALL, // salva/deleta junto com o pedido
        orphanRemoval = true // se remover da lista, deleta do banco
    )
    private List<PedidoItem> itens = new ArrayList<>();

    // getter do id
    public Long getId() { 
        return id; 
    }

    // pega o cliente
    public Usuario getCliente() { 
        return cliente; 
    }

    // define o cliente
    public void setCliente(Usuario cliente) { 
        this.cliente = cliente; 
    }

    // pega a unidade
    public Unidade getUnidade() { 
        return unidade; 
    }

    // define a unidade
    public void setUnidade(Unidade unidade) { 
        this.unidade = unidade; 
    }

    // pega o canal do pedido
    public CanalPedido getCanalPedido() { 
        return canalPedido; 
    }

    // define o canal do pedido
    public void setCanalPedido(CanalPedido canalPedido) { 
        this.canalPedido = canalPedido; 
    }

    // pega o status
    public PedidoStatus getStatus() { 
        return status; 
    }

    // define o status
    public void setStatus(PedidoStatus status) { 
        this.status = status; 
    }

    // pega o total
    public BigDecimal getTotal() { 
        return total; 
    }

    // define o total
    public void setTotal(BigDecimal total) { 
        this.total = total; 
    }

    // pega a data de criação
    public Instant getCriadoEm() { 
        return criadoEm; 
    }

    // define a data de criação
    public void setCriadoEm(Instant criadoEm) { 
        this.criadoEm = criadoEm; 
    }

    // pega a lista de itens
    public List<PedidoItem> getItens() { 
        return itens; 
    }
}