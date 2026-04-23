package br.com.raizes.backend.application.service;

import br.com.raizes.backend.api.dto.EstoqueDtos;
import br.com.raizes.backend.api.exception.ApiException;
import br.com.raizes.backend.domain.entity.Estoque;
import br.com.raizes.backend.infrastructure.repository.EstoqueRepository;
import br.com.raizes.backend.infrastructure.repository.ProdutoRepository;
import br.com.raizes.backend.infrastructure.repository.UnidadeRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstoqueService {
    private final EstoqueRepository estoqueRepository;
    private final UnidadeRepository unidadeRepository;
    private final ProdutoRepository produtoRepository;

    public EstoqueService(EstoqueRepository estoqueRepository, UnidadeRepository unidadeRepository, ProdutoRepository produtoRepository) {
        this.estoqueRepository = estoqueRepository;
        this.unidadeRepository = unidadeRepository;
        this.produtoRepository = produtoRepository;
    }

    public List<EstoqueDtos.EstoqueResponse> listarPorUnidade(Long unidadeId) {
        return estoqueRepository.findByUnidadeId(unidadeId).stream()
                .map(e -> new EstoqueDtos.EstoqueResponse(
                        e.getUnidade().getId(), e.getProduto().getId(), e.getProduto().getNome(), e.getQuantidade()
                ))
                .toList();
    }

    @Transactional
    public EstoqueDtos.EstoqueResponse entrada(EstoqueDtos.MovimentoRequest request) {
        Estoque estoque = getOrCreate(request.unidadeId(), request.produtoId());
        estoque.setQuantidade(estoque.getQuantidade() + request.quantidade());
        estoque = estoqueRepository.save(estoque);
        return new EstoqueDtos.EstoqueResponse(estoque.getUnidade().getId(), estoque.getProduto().getId(), estoque.getProduto().getNome(), estoque.getQuantidade());
    }

    @Transactional
    public EstoqueDtos.EstoqueResponse saida(EstoqueDtos.MovimentoRequest request) {
        Estoque estoque = getOrCreate(request.unidadeId(), request.produtoId());
        if (estoque.getQuantidade() < request.quantidade()) {
            throw new ApiException("ESTOQUE_INSUFICIENTE", "Nao ha quantidade suficiente em estoque.", HttpStatus.CONFLICT);
        }
        estoque.setQuantidade(estoque.getQuantidade() - request.quantidade());
        estoque = estoqueRepository.save(estoque);
        return new EstoqueDtos.EstoqueResponse(estoque.getUnidade().getId(), estoque.getProduto().getId(), estoque.getProduto().getNome(), estoque.getQuantidade());
    }

    public Estoque getOrCreate(Long unidadeId, Long produtoId) {
        var unidade = unidadeRepository.findById(unidadeId)
                .orElseThrow(() -> new ApiException("UNIDADE_NAO_ENCONTRADA", "Unidade nao encontrada.", HttpStatus.NOT_FOUND));
        var produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ApiException("PRODUTO_NAO_ENCONTRADO", "Produto nao encontrado.", HttpStatus.NOT_FOUND));
        return estoqueRepository.findByUnidadeIdAndProdutoId(unidadeId, produtoId)
                .orElseGet(() -> {
                    Estoque novo = new Estoque();
                    novo.setUnidade(unidade);
                    novo.setProduto(produto);
                    novo.setQuantidade(0);
                    return novo;
                });
    }
}
