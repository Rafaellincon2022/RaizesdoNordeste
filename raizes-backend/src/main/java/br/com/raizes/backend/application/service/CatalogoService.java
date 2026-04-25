package br.com.raizes.backend.application.service;

import br.com.raizes.backend.api.dto.CatalogoDtos;
import br.com.raizes.backend.api.exception.ApiException;
import br.com.raizes.backend.domain.entity.Produto;
import br.com.raizes.backend.infrastructure.repository.ProdutoRepository;
import br.com.raizes.backend.infrastructure.repository.UnidadeRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

// service responsável pelas regras de negócio do catálogo
@Service
public class CatalogoService {

// repositório de unidades (acesso ao banco)
private final UnidadeRepository unidadeRepository;

// repositório de produtos
private final ProdutoRepository produtoRepository;

// construtor para injetar os repositórios
public CatalogoService(UnidadeRepository unidadeRepository, ProdutoRepository produtoRepository) {
    this.unidadeRepository = unidadeRepository;
    this.produtoRepository = produtoRepository;
}

// lista todas as unidades
public List<CatalogoDtos.UnidadeResponse> listarUnidades() {

    // busca tudo no banco, percorre a lista e transforma em DTO
    return unidadeRepository.findAll().stream()
            .map(u -> new CatalogoDtos.UnidadeResponse(
                    u.getId(),
                    u.getNome(),
                    u.getCidade(),
                    u.isAtiva()
            ))
            .toList(); // retorna como lista
}

// lista todos os produtos
public List<CatalogoDtos.ProdutoResponse> listarProdutos() {

    // mesma ideia das unidades
    return produtoRepository.findAll().stream()
            .map(p -> new CatalogoDtos.ProdutoResponse(
                    p.getId(),
                    p.getNome(),
                    p.getPreco(),
                    p.isAtivo()
            ))
            .toList();
}

// cria um novo produto
public CatalogoDtos.ProdutoResponse criarProduto(CatalogoDtos.ProdutoRequest request) {

    // cria objeto produto
    Produto produto = new Produto();

    // preenche com dados do request
    produto.setNome(request.nome());
    produto.setPreco(request.preco());
    produto.setAtivo(request.ativo());

    // salva no banco
    Produto salvo = produtoRepository.save(produto);

    // retorna os dados do produto salvo
    return new CatalogoDtos.ProdutoResponse(
            salvo.getId(),
            salvo.getNome(),
            salvo.getPreco(),
            salvo.isAtivo()
    );
}

// atualiza um produto existente
public CatalogoDtos.ProdutoResponse atualizarProduto(Long id, CatalogoDtos.ProdutoRequest request) {

    // busca o produto pelo id
    Produto produto = produtoRepository.findById(id)
            .orElseThrow(() -> new ApiException(
                    "PRODUTO_NAO_ENCONTRADO",
                    "Produto nao encontrado.",
                    HttpStatus.NOT_FOUND
            ));

    // atualiza os dados
    produto.setNome(request.nome());
    produto.setPreco(request.preco());
    produto.setAtivo(request.ativo());

    // salva novamente (update)
    Produto salvo = produtoRepository.save(produto);

    // retorna atualizado
    return new CatalogoDtos.ProdutoResponse(
            salvo.getId(),
            salvo.getNome(),
            salvo.getPreco(),
            salvo.isAtivo()
    );
}

}
