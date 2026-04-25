package br.com.raizes.backend.application.service;

import br.com.raizes.backend.api.dto.AuthDtos;
import br.com.raizes.backend.api.exception.ApiException;
import br.com.raizes.backend.domain.entity.Usuario;
import br.com.raizes.backend.infrastructure.repository.UsuarioRepository;
import br.com.raizes.backend.infrastructure.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// essa classe é um service, ou seja, aqui fica a regra de negócio de autenticação
@Service
public class AuthService {

// repositório para acessar os usuários no banco
private final UsuarioRepository usuarioRepository;

// usado para criptografar a senha
private final PasswordEncoder passwordEncoder;

// responsável por gerar o token JWT
private final JwtService jwtService;

// usado pelo Spring Security para validar login
private final AuthenticationManager authenticationManager;

// construtor para injetar as dependências
public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
    this.usuarioRepository = usuarioRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
}

// método para registrar um novo usuário
public AuthDtos.UserView register(AuthDtos.RegisterRequest request) {

    // verifica se já existe usuário com esse email
    usuarioRepository.findByEmail(request.email()).ifPresent(u -> {
        // se existir, lança erro
        throw new ApiException("EMAIL_JA_EXISTE", "E-mail ja cadastrado.", HttpStatus.CONFLICT);
    });

    // cria um novo usuário
    Usuario usuario = new Usuario();

    // seta os dados vindos do request
    usuario.setNome(request.nome());
    usuario.setEmail(request.email());
    usuario.setRole(request.role());
    usuario.setConsentimentoLgpd(request.consentimentoLgpd());

    // criptografa a senha antes de salvar (importante!)
    usuario.setSenhaHash(passwordEncoder.encode(request.senha()));

    // salva no banco
    usuario = usuarioRepository.save(usuario);

    // retorna os dados do usuário (sem senha)
    return new AuthDtos.UserView(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getRole()
    );
}

// método de login
public AuthDtos.AuthResponse login(AuthDtos.LoginRequest request) {

    try {
        // tenta autenticar com email e senha
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.senha())
        );
    } catch (Exception ex) {
        // se der erro, significa que login falhou
        throw new ApiException("CREDENCIAIS_INVALIDAS", "E-mail ou senha invalidos.", HttpStatus.UNAUTHORIZED);
    }

    // busca o usuário no banco pelo email
    Usuario usuario = usuarioRepository.findByEmail(request.email())
            .orElseThrow(() -> new ApiException("USUARIO_NAO_ENCONTRADO", "Usuario nao encontrado.", HttpStatus.NOT_FOUND));

    // gera o token JWT para o usuário
    String token = jwtService.generateToken(usuario);

    // retorna o token + dados do usuário
    return new AuthDtos.AuthResponse(
            token, // token JWT
            "Bearer", // tipo do token
            jwtService.getExpirationSeconds(), // tempo de expiração
            new AuthDtos.UserView(
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getRole()
            )
    );
}

}
