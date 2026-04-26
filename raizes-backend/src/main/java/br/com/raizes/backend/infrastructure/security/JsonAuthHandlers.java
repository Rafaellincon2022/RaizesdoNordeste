package br.com.raizes.backend.infrastructure.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

// Classe responsável por tratar erros de autenticação e autorização
@Component
public class JsonAuthHandlers implements AuthenticationEntryPoint, AccessDeniedHandler {

    // Usado para converter objetos em JSON
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Chamado quando o usuário NÃO está autenticado (ex: sem token)
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException authException) throws IOException {

        // Retorna erro 401
        write(response, HttpStatus.UNAUTHORIZED, "NAO_AUTENTICADO", "Token ausente ou invalido.", request.getRequestURI());
    }

    // Chamado quando o usuário está autenticado, mas não tem permissão
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {

        // Retorna erro 403
        write(response, HttpStatus.FORBIDDEN, "SEM_PERMISSAO", "Perfil sem permissao para este recurso.", request.getRequestURI());
    }

    // Método auxiliar que monta a resposta em JSON
    private void write(HttpServletResponse response, HttpStatus status, String error, String message, String path) throws IOException {

        // Define o status HTTP (401 ou 403)
        response.setStatus(status.value());

        // Define que a resposta será JSON
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // Escreve o JSON na resposta
        objectMapper.writeValue(
                response.getWriter(),
                new ErrorBody(
                        error,
                        message,
                        List.of(), // detalhes adicionais (vazio aqui)
                        Instant.now().toString(), // data/hora atual
                        path // endpoint que gerou o erro
                )
        );
    }

    // Estrutura do JSON de erro
    record ErrorBody(
            String error,
            String message,
            List<Object> details,
            String timestamp,
            String path
    ) {}
}