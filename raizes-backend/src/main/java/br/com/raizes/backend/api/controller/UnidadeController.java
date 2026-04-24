package br.com.raizes.backend.api.controller; // pasta/controller, organiza arquivos

import java.util.List; // lista de unidades
import org.springframework.web.bind.annotation.GetMapping; // rota GET
import org.springframework.web.bind.annotation.RequestMapping; // define caminho base
import org.springframework.web.bind.annotation.RestController; // define controller REST
import br.com.raizes.backend.api.dto.CatalogoDtos; // pega caixinhas de dados de unidades
import br.com.raizes.backend.application.service.CatalogoService; // serviço que faz a lógica de unidades
import io.swagger.v3.oas.annotations.security.SecurityRequirement; // Swagger mostra que precisa de token

@RestController // fala pro Spring que a classe responde requisições e devolve JSON
@RequestMapping("/unidades") // todas as rotas começam com /unidades
@SecurityRequirement(name = "bearerAuth") // Swagger mostra que precisa de JWT
public class UnidadeController {

    private final CatalogoService catalogoService; // serviço que faz o trabalho real

    public UnidadeController(CatalogoService catalogoService) { // construtor, pega o serviço e guarda aqui
        this.catalogoService = catalogoService;
    }

    @GetMapping // rota GET /unidades
    public List<CatalogoDtos.UnidadeResponse> listar() {
        return catalogoService.listarUnidades(); // chama serviço e devolve lista de unidades
    }
}