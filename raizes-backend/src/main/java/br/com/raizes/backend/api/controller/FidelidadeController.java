package br.com.raizes.backend.api.controller; // pasta/controller, organiza arquivos

import br.com.raizes.backend.api.dto.FidelidadeDtos; // pega as caixinhas de dados
import br.com.raizes.backend.application.service.FidelidadeService; // serviço que faz a lógica de fidelidade
import jakarta.validation.Valid; // valida os dados que o cliente manda
import org.springframework.security.access.prepost.PreAuthorize; // controla quem pode acessar
import org.springframework.web.bind.annotation.*; // anotações de rota do Spring
import io.swagger.v3.oas.annotations.security.SecurityRequirement; // Swagger mostra que precisa de token

@RestController // fala pro Spring que essa classe responde requisições e devolve JSON
@RequestMapping("/fidelidade") // todas as rotas começam com /fidelidade
@SecurityRequirement(name = "bearerAuth") // fala pro Swagger que precisa de JWT
public class FidelidadeController {

    private final FidelidadeService fidelidadeService; // serviço que faz o trabalho real

    public FidelidadeController(FidelidadeService fidelidadeService) { // construtor, pega o serviço e guarda aqui
        this.fidelidadeService = fidelidadeService;
    }

    @GetMapping("/usuarios/{usuarioId}/saldo") // rota GET /fidelidade/usuarios/1/saldo
    @PreAuthorize("hasAnyRole('CLIENTE','ATENDENTE','ADMIN','GERENTE')") // só roles certas podem acessar
    public FidelidadeDtos.SaldoResponse saldo(@PathVariable Long usuarioId) {
        return fidelidadeService.consultarSaldo(usuarioId); // chama serviço pra ver saldo
    }

    @PostMapping("/usuarios/{usuarioId}/resgates") // rota POST /fidelidade/usuarios/1/resgates
    @PreAuthorize("hasAnyRole('CLIENTE','ADMIN','GERENTE')") // só roles certas podem usar
    public FidelidadeDtos.SaldoResponse resgatar(
            @PathVariable Long usuarioId, // pega id do usuário da URL
            @Valid @RequestBody FidelidadeDtos.ResgateRequest request) { // pega os dados do JSON (quantos pontos resgatar)
        return fidelidadeService.resgatar(usuarioId, request.pontos()); // chama serviço e devolve saldo atualizado
    }
}