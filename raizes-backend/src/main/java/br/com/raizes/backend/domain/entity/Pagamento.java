package br.com.raizes.backend.domain.entity;

import br.com.raizes.backend.domain.enums.PagamentoStatus;
import jakarta.persistence.*;
import java.time.Instant;

// essa classe representa um pagamento de um pedido
@Entity

// nome da tabela no banco
@Table(name = "pagamentos")
public class Pagamento {

    // id da tabela (chave primária)
    @Id

    // o banco gera automaticamente
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // relacionamento 1 pra 1 com pedido
    // cada pedido tem um único pagamento
    @OneToOne(optional = false)

    // coluna no banco que referencia o pedido
    // unique = true -> não permite dois pagamentos pro mesmo pedido
    @JoinColumn(name = "pedido_id", unique = true)
    private Pedido pedido;

    // status do pagamento (usa enum)
    // vai ser salvo como texto no banco (STRING)
    @Enumerated(EnumType.STRING)

    // não pode ser nulo
    @Column(nullable = false)
    private PagamentoStatus status;

    // aqui pode guardar retorno do gateway de pagamento (tipo JSON ou texto)
    // limite de 1200 caracteres
    @Column(name = "payload_retorno", length = 1200)
    private String payloadRetorno;

    // data e hora que o pagamento foi registrado
    // não pode ser nulo
    @Column(name = "registrado_em", nullable = false)
    private Instant registradoEm;

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

    // pega o status
    public PagamentoStatus getStatus() { 
        return status; 
    }

    // define o status
    public void setStatus(PagamentoStatus status) { 
        this.status = status; 
    }

    // pega o payload de retorno
    public String getPayloadRetorno() { 
        return payloadRetorno; 
    }

    // define o payload de retorno
    public void setPayloadRetorno(String payloadRetorno) { 
        this.payloadRetorno = payloadRetorno; 
    }

    // pega a data de registro
    public Instant getRegistradoEm() { 
        return registradoEm; 
    }

    // define a data de registro
    public void setRegistradoEm(Instant registradoEm) { 
        this.registradoEm = registradoEm; 
    }
}