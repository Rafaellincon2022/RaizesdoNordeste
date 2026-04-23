package br.com.raizes.backend.api.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.raizes.backend.api.dto.PedidoDtos;
import br.com.raizes.backend.application.service.PedidoService;
import br.com.raizes.backend.domain.enums.CanalPedido;
import br.com.raizes.backend.domain.enums.PedidoStatus;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/pedidos")
@SecurityRequirement(name = "bearerAuth")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('CLIENTE','ATENDENTE','ADMIN','GERENTE')")
    public ResponseEntity<PedidoDtos.PedidoResponse> criar(@Valid @RequestBody PedidoDtos.CriarPedidoRequest request, Principal principal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.criar(request, principal.getName()));
    }

    @GetMapping
    public List<PedidoDtos.PedidoResponse> listar(
            @RequestParam(required = false) CanalPedido canalPedido,
            @RequestParam(required = false) PedidoStatus status
    ) {
        return pedidoService.listar(canalPedido, status);
    }

    @PatchMapping("/{pedidoId}/status")
    @PreAuthorize("hasAnyRole('COZINHA','ATENDENTE','ADMIN','GERENTE')")
    public PedidoDtos.PedidoResponse atualizarStatus(
            @PathVariable Long pedidoId,
            @Valid @RequestBody PedidoDtos.AtualizarStatusRequest request,
            Principal principal
    ) {
        return pedidoService.atualizarStatus(pedidoId, request.status(), principal.getName());
    }
}
