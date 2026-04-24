package br.com.raizes.backend.api.controller; // pasta/controller, organiza arquivos

import java.security.Principal; // pega quem está logado
import java.util.List; // lista de pedidos
import org.springframework.http.HttpStatus; // códigos HTTP tipo 200, 201
import org.springframework.http.ResponseEntity; // montar respostas HTTP
import org.springframework.security.access.prepost.PreAuthorize; // controla quem pode acessar
import org.springframework.web.bind.annotation.*; // anotações de rota do Spring
import br.com.raizes.backend.api.dto.PedidoDtos; // pega caixinhas de dados do pedido
import br.com.raizes.backend.application.service.PedidoService; // serviço que faz a lógica de pedido
import br.com.raizes.backend.domain.enums.CanalPedido; // enum do canal do pedido
import br.com.raizes.backend.domain.enums.PedidoStatus; // enum de status do pedido
import io.swagger.v3.oas.annotations.security.SecurityRequirement; // Swagger mostra que precisa de token
import jakarta.validation.Valid; // valida dados que cliente envia

@RestController // fala pro Spring que a classe responde requisições e devolve JSON
@RequestMapping("/pedidos") // todas as rotas começam com /pedidos
@SecurityRequirement(name = "bearerAuth") // fala pro Swagger que precisa de JWT
public class PedidoController {

    private final PedidoService pedidoService; // serviço que faz o trabalho real

    public PedidoController(PedidoService pedidoService) { // construtor, pega o serviço e guarda aqui
        this.pedidoService = pedidoService;
    }

    @PostMapping // rota POST /pedidos
    @PreAuthorize("hasAnyRole('CLIENTE','ATENDENTE','ADMIN','GERENTE')") // só roles certas podem criar pedido
    public ResponseEntity<PedidoDtos.PedidoResponse> criar(
            @Valid @RequestBody PedidoDtos.CriarPedidoRequest request, // pega dados do JSON
            Principal principal) { // pega usuário logado
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pedidoService.criar(request, principal.getName())); // chama serviço e devolve pedido criado
    }

    @GetMapping // rota GET /pedidos
    public List<PedidoDtos.PedidoResponse> listar(
            @RequestParam(required = false) CanalPedido canalPedido, // filtro opcional por canal
            @RequestParam(required = false) PedidoStatus status) { // filtro opcional por status
        return pedidoService.listar(canalPedido, status); // chama serviço e devolve lista de pedidos
    }

    @PatchMapping("/{pedidoId}/status") // rota PATCH /pedidos/1/status
    @PreAuthorize("hasAnyRole('COZINHA','ATENDENTE','ADMIN','GERENTE')") // só roles certas podem atualizar
    public PedidoDtos.PedidoResponse atualizarStatus(
            @PathVariable Long pedidoId, // pega id do pedido da URL
            @Valid @RequestBody PedidoDtos.AtualizarStatusRequest request, // pega novo status do JSON
            Principal principal) { // pega usuário logado
        return pedidoService.atualizarStatus(
                pedidoId, // id do pedido
                request.status(), // novo status
                principal.getName() // nome do usuário logado
        ); // chama serviço e devolve pedido atualizado
    }
}