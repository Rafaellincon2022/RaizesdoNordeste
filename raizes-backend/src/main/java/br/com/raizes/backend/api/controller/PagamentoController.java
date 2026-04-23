package br.com.raizes.backend.api.controller;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.raizes.backend.api.dto.PagamentoDtos;
import br.com.raizes.backend.application.service.PagamentoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pagamentos")
@SecurityRequirement(name = "bearerAuth")
public class PagamentoController {
    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping("/pedidos/{pedidoId}/mock")
    @PreAuthorize("hasAnyRole('ATENDENTE','ADMIN','GERENTE')")
    public PagamentoDtos.PagamentoResponse processarMock(
            @PathVariable Long pedidoId,
            @Valid @RequestBody PagamentoDtos.ProcessarPagamentoRequest request,
            Principal principal
    ) {
        return pagamentoService.processar(pedidoId, request.aprovado(), principal.getName());
    }
}
