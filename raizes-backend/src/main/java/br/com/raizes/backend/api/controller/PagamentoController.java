package br.com.raizes.backend.api.controller; // pasta/controller, organiza arquivos

import java.security.Principal; // pega quem está logado
import org.springframework.security.access.prepost.PreAuthorize; // controla quem pode acessar
import org.springframework.web.bind.annotation.*; // anotações de rota do Spring
import br.com.raizes.backend.api.dto.PagamentoDtos; // pega as caixinhas de dados
import br.com.raizes.backend.application.service.PagamentoService; // serviço que faz a lógica de pagamento
import io.swagger.v3.oas.annotations.security.SecurityRequirement; // Swagger mostra que precisa de token
import jakarta.validation.Valid; // valida os dados que o cliente manda

@RestController // fala pro Spring que a classe responde requisições e devolve JSON
@RequestMapping("/pagamentos") // todas as rotas começam com /pagamentos
@SecurityRequirement(name = "bearerAuth") // fala pro Swagger que precisa de JWT
public class PagamentoController {

    private final PagamentoService pagamentoService; // serviço que faz o trabalho real

    public PagamentoController(PagamentoService pagamentoService) { // construtor, pega o serviço e guarda aqui
        this.pagamentoService = pagamentoService;
    }

    @PostMapping("/pedidos/{pedidoId}/mock") // rota POST /pagamentos/pedidos/1/mock
    @PreAuthorize("hasAnyRole('ATENDENTE','ADMIN','GERENTE')") // só roles certas podem usar
    public PagamentoDtos.PagamentoResponse processarMock(
            @PathVariable Long pedidoId, // pega id do pedido da URL
            @Valid @RequestBody PagamentoDtos.ProcessarPagamentoRequest request, // pega dados do JSON (aprovar ou não)
            Principal principal // pega usuário logado
    ) {
        return pagamentoService.processar(
                pedidoId, // id do pedido
                request.aprovado(), // aprovado ou não
                principal.getName() // nome do usuário logado
        ); // chama serviço e devolve resposta do pagamento
    }
}