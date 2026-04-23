package br.com.raizes.backend.application.service;

import br.com.raizes.backend.api.dto.PagamentoDtos;
import br.com.raizes.backend.api.exception.ApiException;
import br.com.raizes.backend.domain.entity.Pagamento;
import br.com.raizes.backend.domain.enums.PagamentoStatus;
import br.com.raizes.backend.domain.enums.PedidoStatus;
import br.com.raizes.backend.infrastructure.repository.PagamentoRepository;
import br.com.raizes.backend.infrastructure.repository.PedidoRepository;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PagamentoService {
    private final PedidoRepository pedidoRepository;
    private final PagamentoRepository pagamentoRepository;
    private final AuditoriaService auditoriaService;

    public PagamentoService(PedidoRepository pedidoRepository, PagamentoRepository pagamentoRepository, AuditoriaService auditoriaService) {
        this.pedidoRepository = pedidoRepository;
        this.pagamentoRepository = pagamentoRepository;
        this.auditoriaService = auditoriaService;
    }

    @Transactional
    public PagamentoDtos.PagamentoResponse processar(Long pedidoId, boolean aprovado, String actorEmail) {
        var pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new ApiException("PEDIDO_NAO_ENCONTRADO", "Pedido nao encontrado.", HttpStatus.NOT_FOUND));

        Pagamento pagamento = pagamentoRepository.findByPedidoId(pedidoId).orElseGet(Pagamento::new);
        pagamento.setPedido(pedido);
        pagamento.setStatus(aprovado ? PagamentoStatus.APROVADO : PagamentoStatus.RECUSADO);
        pagamento.setPayloadRetorno("{\"gateway\":\"mock\",\"status\":\"" + pagamento.getStatus().name() + "\"}");
        pagamento.setRegistradoEm(Instant.now());
        pagamento = pagamentoRepository.save(pagamento);

        pedido.setStatus(aprovado ? PedidoStatus.PAGO : PedidoStatus.CANCELADO);
        pedidoRepository.save(pedido);

        auditoriaService.registrar(actorEmail, "PAGAMENTO_MOCK", "PEDIDO", pedidoId, "Status pagamento: " + pagamento.getStatus());
        return new PagamentoDtos.PagamentoResponse(pedidoId, pagamento.getStatus(), pagamento.getPayloadRetorno());
    }
}
