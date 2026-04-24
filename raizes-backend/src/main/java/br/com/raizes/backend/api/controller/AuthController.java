package br.com.raizes.backend.api.controller; // pasta/controller, organiza arquivos

import br.com.raizes.backend.api.dto.AuthDtos; // pega os DTOs que são tipo “caixinhas de dados”
import br.com.raizes.backend.application.service.AuthService; // serviço que faz a lógica de autenticação
import jakarta.validation.Valid; // valida os dados que o cliente manda
import org.springframework.http.HttpStatus; // códigos de resposta tipo 200, 201
import org.springframework.http.ResponseEntity; // monta a resposta para o cliente
import org.springframework.web.bind.annotation.*; // coisas do Spring pra criar controller e rotas

@RestController // fala pro Spring que essa classe recebe requisições e devolve JSON
@RequestMapping("/auth") // todas as rotas começam com /auth
public class AuthController {

    private final AuthService authService; // serviço que faz o trabalho de autenticação

    public AuthController(AuthService authService) { // construtor, pega o serviço e guarda aqui
        this.authService = authService;
    }

    @PostMapping("/register") // rota POST /auth/register
    public ResponseEntity<AuthDtos.UserView> register(
            @Valid @RequestBody AuthDtos.RegisterRequest request) { // pega os dados que o cliente enviou
        return ResponseEntity
                .status(HttpStatus.CREATED) // responde 201 criado
                .body(authService.register(request)); // chama o serviço pra criar usuário e devolve
    }

    @PostMapping("/login") // rota POST /auth/login
    public ResponseEntity<AuthDtos.AuthResponse> login(
            @Valid @RequestBody AuthDtos.LoginRequest request) { // pega login do cliente
        return ResponseEntity
                .ok(authService.login(request)); // chama serviço, devolve token e info do login
    }
}