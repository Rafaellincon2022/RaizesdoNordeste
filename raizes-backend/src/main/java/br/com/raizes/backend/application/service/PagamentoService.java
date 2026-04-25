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

// service responsável por processar pagamentos
@Service
public class PagamentoService {

private final PedidoRepository pedidoRepository;
private final PagamentoRepository pagamentoRepository;
private final AuditoriaService auditoriaService;

// construtor com injeção de dependências
public PagamentoService(PedidoRepository pedidoRepository, PagamentoRepository pagamentoRepository, AuditoriaService auditoriaService) {
    this.pedidoRepository = pedidoRepository;
    this.pagamentoRepository = pagamentoRepository;
    this.auditoriaService = auditoriaService;
}

// processa o pagamento de um pedido
@Transactional
public PagamentoDtos.PagamentoResponse processar(Long pedidoId, boolean aprovado, String actorEmail) {

    // busca o pedido pelo id
    var pedido = pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new ApiException(
                    "PEDIDO_NAO_ENCONTRADO",
                    "Pedido nao encontrado.",
                    HttpStatus.NOT_FOUND
            ));

    // tenta encontrar pagamento existente ou cria um novo
    Pagamento pagamento = pagamentoRepository.findByPedidoId(pedidoId)
            .orElseGet(Pagamento::new);

    // associa o pagamento ao pedido
    pagamento.setPedido(pedido);

    // define o status dependendo se foi aprovado ou não
    pagamento.setStatus(aprovado ? PagamentoStatus.APROVADO : PagamentoStatus.RECUSADO);

    // simula retorno de um gateway de pagamento (mock)
    pagamento.setPayloadRetorno(
            "{\"gateway\":\"mock\",\"status\":\"" + pagamento.getStatus().name() + "\"}"
    );

    // registra data/hora
    pagamento.setRegistradoEm(Instant.now());

    // salva pagamento
    pagamento = pagamentoRepository.save(pagamento);

    // atualiza status do pedido
    pedido.setStatus(aprovado ? PedidoStatus.PAGO : PedidoStatus.CANCELADO);

    // salva pedido
    pedidoRepository.save(pedido);

    // registra log/auditoria da ação
    auditoriaService.registrar(
            actorEmail,
            "PAGAMENTO_MOCK",
            "PEDIDO",
            pedidoId,
            "Status pagamento: " + pagamento.getStatus()
    );

    // retorna resposta
    return new PagamentoDtos.PagamentoResponse(
            pedidoId,
            pagamento.getStatus(),
            pagamento.getPayloadRetorno()
    );
}

}
