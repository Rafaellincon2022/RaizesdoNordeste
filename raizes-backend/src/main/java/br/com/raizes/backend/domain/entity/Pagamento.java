package br.com.raizes.backend.domain.entity;

import br.com.raizes.backend.domain.enums.PagamentoStatus;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "pagamentos")
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(optional = false)
    @JoinColumn(name = "pedido_id", unique = true)
    private Pedido pedido;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PagamentoStatus status;
    @Column(name = "payload_retorno", length = 1200)
    private String payloadRetorno;
    @Column(name = "registrado_em", nullable = false)
    private Instant registradoEm;

    public Long getId() { return id; }
    public Pedido getPedido() { return pedido; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
    public PagamentoStatus getStatus() { return status; }
    public void setStatus(PagamentoStatus status) { this.status = status; }
    public String getPayloadRetorno() { return payloadRetorno; }
    public void setPayloadRetorno(String payloadRetorno) { this.payloadRetorno = payloadRetorno; }
    public Instant getRegistradoEm() { return registradoEm; }
    public void setRegistradoEm(Instant registradoEm) { this.registradoEm = registradoEm; }
}
