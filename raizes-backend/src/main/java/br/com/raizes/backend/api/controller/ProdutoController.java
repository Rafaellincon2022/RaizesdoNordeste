package br.com.raizes.backend.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.raizes.backend.api.dto.CatalogoDtos;
import br.com.raizes.backend.application.service.CatalogoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
@SecurityRequirement(name = "bearerAuth")
public class ProdutoController {
    private final CatalogoService catalogoService;

    public ProdutoController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    @GetMapping
    public List<CatalogoDtos.ProdutoResponse> listar() {
        return catalogoService.listarProdutos();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public ResponseEntity<CatalogoDtos.ProdutoResponse> criar(@Valid @RequestBody CatalogoDtos.ProdutoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogoService.criarProduto(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public CatalogoDtos.ProdutoResponse atualizar(@PathVariable Long id, @Valid @RequestBody CatalogoDtos.ProdutoRequest request) {
        return catalogoService.atualizarProduto(id, request);
    }
}
