package br.com.raizes.backend.api.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.raizes.backend.api.dto.EstoqueDtos;
import br.com.raizes.backend.application.service.EstoqueService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/estoque")
@SecurityRequirement(name = "bearerAuth")
public class EstoqueController {
    private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService) {
        this.estoqueService = estoqueService;
    }

    @GetMapping("/unidades/{unidadeId}")
    public List<EstoqueDtos.EstoqueResponse> listarPorUnidade(@PathVariable Long unidadeId) {
        return estoqueService.listarPorUnidade(unidadeId);
    }

    @PostMapping("/entrada")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','ATENDENTE')")
    public EstoqueDtos.EstoqueResponse entrada(@Valid @RequestBody EstoqueDtos.MovimentoRequest request) {
        return estoqueService.entrada(request);
    }

    @PostMapping("/saida")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','ATENDENTE')")
    public EstoqueDtos.EstoqueResponse saida(@Valid @RequestBody EstoqueDtos.MovimentoRequest request) {
        return estoqueService.saida(request);
    }
}
