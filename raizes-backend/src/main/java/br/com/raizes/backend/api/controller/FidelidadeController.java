package br.com.raizes.backend.api.controller;

import br.com.raizes.backend.api.dto.FidelidadeDtos;
import br.com.raizes.backend.application.service.FidelidadeService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/fidelidade")
@SecurityRequirement(name = "bearerAuth")
public class FidelidadeController {
    private final FidelidadeService fidelidadeService;

    public FidelidadeController(FidelidadeService fidelidadeService) {
        this.fidelidadeService = fidelidadeService;
    }

    @GetMapping("/usuarios/{usuarioId}/saldo")
    @PreAuthorize("hasAnyRole('CLIENTE','ATENDENTE','ADMIN','GERENTE')")
    public FidelidadeDtos.SaldoResponse saldo(@PathVariable Long usuarioId) {
        return fidelidadeService.consultarSaldo(usuarioId);
    }

    @PostMapping("/usuarios/{usuarioId}/resgates")
    @PreAuthorize("hasAnyRole('CLIENTE','ADMIN','GERENTE')")
    public FidelidadeDtos.SaldoResponse resgatar(@PathVariable Long usuarioId, @Valid @RequestBody FidelidadeDtos.ResgateRequest request) {
        return fidelidadeService.resgatar(usuarioId, request.pontos());
    }
}
