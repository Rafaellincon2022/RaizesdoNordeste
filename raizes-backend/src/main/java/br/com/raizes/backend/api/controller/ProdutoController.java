package br.com.raizes.backend.api.controller; // pasta/controller, organiza arquivos

import java.util.List; // lista de produtos
import org.springframework.http.HttpStatus; // códigos HTTP tipo 200, 201
import org.springframework.http.ResponseEntity; // montar respostas HTTP
import org.springframework.security.access.prepost.PreAuthorize; // controla quem pode acessar
import org.springframework.web.bind.annotation.*; // anotações de rota do Spring
import br.com.raizes.backend.api.dto.CatalogoDtos; // pega caixinhas de dados do produto
import br.com.raizes.backend.application.service.CatalogoService; // serviço que faz a lógica de produtos
import io.swagger.v3.oas.annotations.security.SecurityRequirement; // Swagger mostra que precisa de token
import jakarta.validation.Valid; // valida dados que cliente envia

@RestController // fala pro Spring que a classe responde requisições e devolve JSON
@RequestMapping("/produtos") // todas as rotas começam com /produtos
@SecurityRequirement(name = "bearerAuth") // fala pro Swagger que precisa de JWT
public class ProdutoController {

    private final CatalogoService catalogoService; // serviço que faz o trabalho real

    public ProdutoController(CatalogoService catalogoService) { // construtor, pega o serviço e guarda aqui
        this.catalogoService = catalogoService;
    }

    @GetMapping // rota GET /produtos
    public List<CatalogoDtos.ProdutoResponse> listar() {
        return catalogoService.listarProdutos(); // chama serviço e devolve lista de produtos
    }

    @PostMapping // rota POST /produtos
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')") // só ADMIN e GERENTE podem criar
    public ResponseEntity<CatalogoDtos.ProdutoResponse> criar(
            @Valid @RequestBody CatalogoDtos.ProdutoRequest request) { // pega dados do JSON e valida
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(catalogoService.criarProduto(request)); // chama serviço e devolve produto criado
    }

    @PutMapping("/{id}") // rota PUT /produtos/1
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')") // só ADMIN e GERENTE podem atualizar
    public CatalogoDtos.ProdutoResponse atualizar(
            @PathVariable Long id, // pega ID do produto da URL
            @Valid @RequestBody CatalogoDtos.ProdutoRequest request) { // pega dados do JSON e valida
        return catalogoService.atualizarProduto(id, request); // chama serviço e devolve produto atualizado
    }
}