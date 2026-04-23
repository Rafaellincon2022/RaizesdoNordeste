package br.com.raizes.backend.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.raizes.backend.api.dto.CatalogoDtos;
import br.com.raizes.backend.application.service.CatalogoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/unidades")
@SecurityRequirement(name = "bearerAuth")
public class UnidadeController {
    private final CatalogoService catalogoService;

    public UnidadeController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    @GetMapping
    public List<CatalogoDtos.UnidadeResponse> listar() {
        return catalogoService.listarUnidades();
    }
}
