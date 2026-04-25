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

// service responsável por controlar o estoque
@Service
public class EstoqueService {

private final EstoqueRepository estoqueRepository;
private final UnidadeRepository unidadeRepository;
private final ProdutoRepository produtoRepository;

// injeta os repositórios
public EstoqueService(EstoqueRepository estoqueRepository, UnidadeRepository unidadeRepository, ProdutoRepository produtoRepository) {
    this.estoqueRepository = estoqueRepository;
    this.unidadeRepository = unidadeRepository;
    this.produtoRepository = produtoRepository;
}

// lista o estoque de uma unidade específica
public List<EstoqueDtos.EstoqueResponse> listarPorUnidade(Long unidadeId) {

    // busca pelo id da unidade e transforma em DTO
    return estoqueRepository.findByUnidadeId(unidadeId).stream()
            .map(e -> new EstoqueDtos.EstoqueResponse(
                    e.getUnidade().getId(),
                    e.getProduto().getId(),
                    e.getProduto().getNome(),
                    e.getQuantidade()
            ))
            .toList();
}

// entrada de produto no estoque (adiciona quantidade)
@Transactional
public EstoqueDtos.EstoqueResponse entrada(EstoqueDtos.MovimentoRequest request) {

    // pega o estoque ou cria se não existir
    Estoque estoque = getOrCreate(request.unidadeId(), request.produtoId());

    // soma a quantidade atual com a nova
    estoque.setQuantidade(estoque.getQuantidade() + request.quantidade());

    // salva no banco
    estoque = estoqueRepository.save(estoque);

    // retorna os dados atualizados
    return new EstoqueDtos.EstoqueResponse(
            estoque.getUnidade().getId(),
            estoque.getProduto().getId(),
            estoque.getProduto().getNome(),
            estoque.getQuantidade()
    );
}

// saída de produto (remove do estoque)
@Transactional
public EstoqueDtos.EstoqueResponse saida(EstoqueDtos.MovimentoRequest request) {

    // pega o estoque ou cria
    Estoque estoque = getOrCreate(request.unidadeId(), request.produtoId());

    // verifica se tem quantidade suficiente
    if (estoque.getQuantidade() < request.quantidade()) {
        throw new ApiException(
                "ESTOQUE_INSUFICIENTE",
                "Nao ha quantidade suficiente em estoque.",
                HttpStatus.CONFLICT
        );
    }

    // diminui a quantidade
    estoque.setQuantidade(estoque.getQuantidade() - request.quantidade());

    // salva no banco
    estoque = estoqueRepository.save(estoque);

    // retorna atualizado
    return new EstoqueDtos.EstoqueResponse(
            estoque.getUnidade().getId(),
            estoque.getProduto().getId(),
            estoque.getProduto().getNome(),
            estoque.getQuantidade()
    );
}

// método auxiliar para buscar ou criar estoque
public Estoque getOrCreate(Long unidadeId, Long produtoId) {

    // busca a unidade
    var unidade = unidadeRepository.findById(unidadeId)
            .orElseThrow(() -> new ApiException(
                    "UNIDADE_NAO_ENCONTRADA",
                    "Unidade nao encontrada.",
                    HttpStatus.NOT_FOUND
            ));

    // busca o produto
    var produto = produtoRepository.findById(produtoId)
            .orElseThrow(() -> new ApiException(
                    "PRODUTO_NAO_ENCONTRADO",
                    "Produto nao encontrado.",
                    HttpStatus.NOT_FOUND
            ));

    // tenta encontrar o estoque existente
    return estoqueRepository.findByUnidadeIdAndProdutoId(unidadeId, produtoId)
            .orElseGet(() -> {
                // se não existir, cria um novo
                Estoque novo = new Estoque();
                novo.setUnidade(unidade);
                novo.setProduto(produto);
                novo.setQuantidade(0); // começa com zero
                return novo;
            });
}

}
