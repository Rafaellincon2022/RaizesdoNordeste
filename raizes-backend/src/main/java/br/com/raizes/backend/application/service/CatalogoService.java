package br.com.raizes.backend.application.service;

import br.com.raizes.backend.api.dto.CatalogoDtos;
import br.com.raizes.backend.api.exception.ApiException;
import br.com.raizes.backend.domain.entity.Produto;
import br.com.raizes.backend.infrastructure.repository.ProdutoRepository;
import br.com.raizes.backend.infrastructure.repository.UnidadeRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CatalogoService {
    private final UnidadeRepository unidadeRepository;
    private final ProdutoRepository produtoRepository;

    public CatalogoService(UnidadeRepository unidadeRepository, ProdutoRepository produtoRepository) {
        this.unidadeRepository = unidadeRepository;
        this.produtoRepository = produtoRepository;
    }

    public List<CatalogoDtos.UnidadeResponse> listarUnidades() {
        return unidadeRepository.findAll().stream()
                .map(u -> new CatalogoDtos.UnidadeResponse(u.getId(), u.getNome(), u.getCidade(), u.isAtiva()))
                .toList();
    }

    public List<CatalogoDtos.ProdutoResponse> listarProdutos() {
        return produtoRepository.findAll().stream()
                .map(p -> new CatalogoDtos.ProdutoResponse(p.getId(), p.getNome(), p.getPreco(), p.isAtivo()))
                .toList();
    }

    public CatalogoDtos.ProdutoResponse criarProduto(CatalogoDtos.ProdutoRequest request) {
        Produto produto = new Produto();
        produto.setNome(request.nome());
        produto.setPreco(request.preco());
        produto.setAtivo(request.ativo());
        Produto salvo = produtoRepository.save(produto);
        return new CatalogoDtos.ProdutoResponse(salvo.getId(), salvo.getNome(), salvo.getPreco(), salvo.isAtivo());
    }

    public CatalogoDtos.ProdutoResponse atualizarProduto(Long id, CatalogoDtos.ProdutoRequest request) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ApiException("PRODUTO_NAO_ENCONTRADO", "Produto nao encontrado.", HttpStatus.NOT_FOUND));
        produto.setNome(request.nome());
        produto.setPreco(request.preco());
        produto.setAtivo(request.ativo());
        Produto salvo = produtoRepository.save(produto);
        return new CatalogoDtos.ProdutoResponse(salvo.getId(), salvo.getNome(), salvo.getPreco(), salvo.isAtivo());
    }
}
