package br.com.raizes.backend.api.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Classe global que captura erros da aplicação
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Trata erros personalizados (ApiException)
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException ex, HttpServletRequest request) {

        // Monta resposta de erro com dados da exceção
        return buildError(
                ex.getStatus(),
                ex.getErrorCode(),
                ex.getMessage(),
                List.of(),
                request.getRequestURI()
        );
    }

    // Trata erros de validação dos DTOs (ex: @NotBlank, @Email)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {

        // Lista para armazenar erros de cada campo
        List<ErrorDetail> details = new ArrayList<>();

        // Percorre todos os erros encontrados
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {

            // Adiciona o nome do campo e a mensagem de erro
            details.add(new ErrorDetail(
                    error.getField(),
                    error.getDefaultMessage()
            ));
        }

        // Retorna erro 422 com lista de detalhes
        return buildError(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "VALIDACAO_FALHOU",
                "Dados invalidos.",
                details,
                request.getRequestURI()
        );
    }

    // Trata erros de validação mais simples (constraint)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraint(ConstraintViolationException ex, HttpServletRequest request) {

        return buildError(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "VALIDACAO_FALHOU",
                ex.getMessage(),
                List.of(),
                request.getRequestURI()
        );
    }

    // Trata qualquer erro não esperado
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {

        return buildError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "ERRO_INTERNO",
                "Erro interno inesperado.",
                List.of(),
                request.getRequestURI()
        );
    }

    // Método auxiliar para montar o padrão de resposta de erro
    private ResponseEntity<ErrorResponse> buildError(
            HttpStatus status,
            String error,
            String message,
            List<ErrorDetail> details,
            String path
    ) {

        // Retorna o status HTTP e o corpo da resposta
        return ResponseEntity.status(status).body(

                // Cria o objeto de resposta de erro
                new ErrorResponse(
                        error,
                        message,
                        details,
                        Instant.now().toString(), // data/hora atual
                        path // endpoint que deu erro
                )
        );
    }

    // Estrutura padrão da resposta de erro
    public record ErrorResponse(

            // Código do erro
            String error,

            // Mensagem do erro
            String message,

            // Lista de detalhes (ex: erros de campos)
            List<ErrorDetail> details,

            // Data/hora do erro
            String timestamp,

            // Caminho da requisição
            String path

    ) {}

    // Detalhe de erro por campo (ex: email inválido)
    public record ErrorDetail(

            // Nome do campo
            String field,

            // Descrição do problema
            String issue

    ) {}
}