package br.com.raizes.backend.application.service;

import br.com.raizes.backend.api.dto.PedidoDtos;
import br.com.raizes.backend.api.exception.ApiException;
import br.com.raizes.backend.domain.entity.Pedido;
import br.com.raizes.backend.domain.entity.PedidoItem;
import br.com.raizes.backend.domain.enums.CanalPedido;
import br.com.raizes.backend.domain.enums.PedidoStatus;
import br.com.raizes.backend.infrastructure.repository.EstoqueRepository;
import br.com.raizes.backend.infrastructure.repository.PedidoRepository;
import br.com.raizes.backend.infrastructure.repository.ProdutoRepository;
import br.com.raizes.backend.infrastructure.repository.UnidadeRepository;
import br.com.raizes.backend.infrastructure.repository.UsuarioRepository;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// service principal responsável pelos pedidos
@Service
public class PedidoService {

private final PedidoRepository pedidoRepository;
private final UsuarioRepository usuarioRepository;
private final UnidadeRepository unidadeRepository;
private final ProdutoRepository produtoRepository;
private final EstoqueRepository estoqueRepository;
private final FidelidadeService fidelidadeService;
private final AuditoriaService auditoriaService;

// construtor com todas dependências
public PedidoService(
        PedidoRepository pedidoRepository,
        UsuarioRepository usuarioRepository,
        UnidadeRepository unidadeRepository,
        ProdutoRepository produtoRepository,
        EstoqueRepository estoqueRepository,
        FidelidadeService fidelidadeService,
        AuditoriaService auditoriaService
) {
    this.pedidoRepository = pedidoRepository;
    this.usuarioRepository = usuarioRepository;
    this.unidadeRepository = unidadeRepository;
    this.produtoRepository = produtoRepository;
    this.estoqueRepository = estoqueRepository;
    this.fidelidadeService = fidelidadeService;
    this.auditoriaService = auditoriaService;
}

// cria um novo pedido
@Transactional
public PedidoDtos.PedidoResponse criar(PedidoDtos.CriarPedidoRequest request, String actorEmail) {

    // busca cliente
    var cliente = usuarioRepository.findById(request.clienteId())
            .orElseThrow(() -> new ApiException(
                    "CLIENTE_NAO_ENCONTRADO",
                    "Cliente nao encontrado.",
                    HttpStatus.NOT_FOUND
            ));

    // busca unidade
    var unidade = unidadeRepository.findById(request.unidadeId())
            .orElseThrow(() -> new ApiException(
                    "UNIDADE_NAO_ENCONTRADA",
                    "Unidade nao encontrada.",
                    HttpStatus.NOT_FOUND
            ));

    // cria pedido
    Pedido pedido = new Pedido();
    pedido.setCliente(cliente);
    pedido.setUnidade(unidade);
    pedido.setCanalPedido(request.canalPedido());
    pedido.setStatus(PedidoStatus.AGUARDANDO_PAGAMENTO);
    pedido.setCriadoEm(Instant.now());

    // total começa com zero
    BigDecimal total = BigDecimal.ZERO;

    // percorre os itens do pedido
    for (PedidoDtos.PedidoItemRequest itemReq : request.itens()) {

        // busca produto
        var produto = produtoRepository.findById(itemReq.produtoId())
                .orElseThrow(() -> new ApiException(
                        "PRODUTO_NAO_ENCONTRADO",
                        "Produto nao encontrado.",
                        HttpStatus.NOT_FOUND
                ));

        // verifica estoque
        var estoque = estoqueRepository.findByUnidadeIdAndProdutoId(unidade.getId(), produto.getId())
                .orElseThrow(() -> new ApiException(
                        "ESTOQUE_NAO_ENCONTRADO",
                        "Produto sem estoque na unidade.",
                        HttpStatus.CONFLICT
                ));

        // valida quantidade
        if (estoque.getQuantidade() < itemReq.quantidade()) {
            throw new ApiException(
                    "ESTOQUE_INSUFICIENTE",
                    "Nao ha quantidade suficiente para um ou mais itens.",
                    HttpStatus.CONFLICT
            );
        }

        // diminui estoque
        estoque.setQuantidade(estoque.getQuantidade() - itemReq.quantidade());
        estoqueRepository.save(estoque);

        // cria item do pedido
        PedidoItem item = new PedidoItem();
        item.setPedido(pedido);
        item.setProduto(produto);
        item.setQuantidade(itemReq.quantidade());
        item.setPrecoUnitario(produto.getPreco());

        // adiciona item ao pedido
        pedido.getItens().add(item);

        // soma no total
        total = total.add(
                produto.getPreco().multiply(BigDecimal.valueOf(itemReq.quantidade()))
        );
    }

    // define total do pedido
    pedido.setTotal(total);

    // salva pedido
    Pedido salvo = pedidoRepository.save(pedido);

    // adiciona pontos de fidelidade (baseado no valor)
    fidelidadeService.acumular(cliente.getId(), total.intValue());

    // registra auditoria
    auditoriaService.registrar(
            actorEmail,
            "CRIAR_PEDIDO",
            "PEDIDO",
            salvo.getId(),
            "Pedido criado com canal " + salvo.getCanalPedido()
    );

    // retorna resposta
    return toResponse(salvo);
}

// lista pedidos com filtros opcionais
public List<PedidoDtos.PedidoResponse> listar(CanalPedido canalPedido, PedidoStatus status) {

    List<Pedido> pedidos;

    if (canalPedido != null && status != null) {
        pedidos = pedidoRepository.findByCanalPedidoAndStatus(canalPedido, status);
    } else if (canalPedido != null) {
        pedidos = pedidoRepository.findByCanalPedido(canalPedido);
    } else if (status != null) {
        pedidos = pedidoRepository.findByStatus(status);
    } else {
        pedidos = pedidoRepository.findAll();
    }

    // converte para DTO
    return pedidos.stream().map(this::toResponse).toList();
}

// atualiza status do pedido
@Transactional
public PedidoDtos.PedidoResponse atualizarStatus(Long pedidoId, PedidoStatus status, String actorEmail) {

    // busca pedido
    Pedido pedido = pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new ApiException(
                    "PEDIDO_NAO_ENCONTRADO",
                    "Pedido nao encontrado.",
                    HttpStatus.NOT_FOUND
            ));

    // atualiza status
    pedido.setStatus(status);

    // salva
    Pedido salvo = pedidoRepository.save(pedido);

    // registra auditoria
    auditoriaService.registrar(
            actorEmail,
            "ATUALIZAR_STATUS_PEDIDO",
            "PEDIDO",
            salvo.getId(),
            "Novo status: " + status
    );

    return toResponse(salvo);
}

// converte entidade para DTO
private PedidoDtos.PedidoResponse toResponse(Pedido pedido) {

    return new PedidoDtos.PedidoResponse(
            pedido.getId(),
            pedido.getStatus(),
            pedido.getCanalPedido(),
            pedido.getTotal(),
            pedido.getCriadoEm().toString(),

            // converte itens também
            pedido.getItens().stream()
                    .map(i -> new PedidoDtos.PedidoItemResponse(
                            i.getProduto().getId(),
                            i.getQuantidade(),
                            i.getPrecoUnitario()
                    ))
                    .toList()
    );
}

}
