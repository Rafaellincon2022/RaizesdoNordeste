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

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthDtos.UserView register(AuthDtos.RegisterRequest request) {
        usuarioRepository.findByEmail(request.email()).ifPresent(u -> {
            throw new ApiException("EMAIL_JA_EXISTE", "E-mail ja cadastrado.", HttpStatus.CONFLICT);
        });
        Usuario usuario = new Usuario();
        usuario.setNome(request.nome());
        usuario.setEmail(request.email());
        usuario.setRole(request.role());
        usuario.setConsentimentoLgpd(request.consentimentoLgpd());
        usuario.setSenhaHash(passwordEncoder.encode(request.senha()));
        usuario = usuarioRepository.save(usuario);
        return new AuthDtos.UserView(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getRole());
    }

    public AuthDtos.AuthResponse login(AuthDtos.LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.senha())
            );
        } catch (Exception ex) {
            throw new ApiException("CREDENCIAIS_INVALIDAS", "E-mail ou senha invalidos.", HttpStatus.UNAUTHORIZED);
        }

        Usuario usuario = usuarioRepository.findByEmail(request.email())
                .orElseThrow(() -> new ApiException("USUARIO_NAO_ENCONTRADO", "Usuario nao encontrado.", HttpStatus.NOT_FOUND));

        String token = jwtService.generateToken(usuario);
        return new AuthDtos.AuthResponse(
                token,
                "Bearer",
                jwtService.getExpirationSeconds(),
                new AuthDtos.UserView(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getRole())
        );
    }
}
