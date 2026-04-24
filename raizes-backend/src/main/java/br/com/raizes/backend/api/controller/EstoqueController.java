package br.com.raizes.backend.api.controller; // pasta/controller, organiza arquivos

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize; // controla quem pode acessar
import org.springframework.web.bind.annotation.*; // anotações de rota do Spring
import br.com.raizes.backend.api.dto.EstoqueDtos; // pega as caixinhas de dados
import br.com.raizes.backend.application.service.EstoqueService; // serviço que faz o trabalho de estoque
import io.swagger.v3.oas.annotations.security.SecurityRequirement; // Swagger mostra que precisa de token
import jakarta.validation.Valid; // valida os dados que o cliente manda

@RestController // fala pro Spring que a classe responde requisições e devolve JSON
@RequestMapping("/estoque") // todas as rotas começam com /estoque
@SecurityRequirement(name = "bearerAuth") // fala pro Swagger que precisa de JWT
public class EstoqueController {

    private final EstoqueService estoqueService; // serviço que faz a lógica do estoque

    public EstoqueController(EstoqueService estoqueService) { // construtor, pega o serviço e guarda aqui
        this.estoqueService = estoqueService;
    }

    @GetMapping("/unidades/{unidadeId}") // rota GET /estoque/unidades/1
    public List<EstoqueDtos.EstoqueResponse> listarPorUnidade(@PathVariable Long unidadeId) {
        return estoqueService.listarPorUnidade(unidadeId); // chama serviço pra listar estoque
    }

    @PostMapping("/entrada") // rota POST /estoque/entrada
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','ATENDENTE')") // só roles certas podem usar
    public EstoqueDtos.EstoqueResponse entrada(@Valid @RequestBody EstoqueDtos.MovimentoRequest request) {
        return estoqueService.entrada(request); // chama serviço pra registrar entrada
    }

    @PostMapping("/saida") // rota POST /estoque/saida
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','ATENDENTE')") // só roles certas podem usar
    public EstoqueDtos.EstoqueResponse saida(@Valid @RequestBody EstoqueDtos.MovimentoRequest request) {
        return estoqueService.saida(request); // chama serviço pra registrar saída
    }
}