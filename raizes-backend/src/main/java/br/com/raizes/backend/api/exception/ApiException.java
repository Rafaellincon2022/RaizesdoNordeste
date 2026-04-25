package br.com.raizes.backend.api.exception;

import org.springframework.http.HttpStatus;

// Classe de exceção personalizada da API
// Usada para lançar erros mais organizados no sistema
public class ApiException extends RuntimeException {

    // Código interno do erro (ex: USER_NOT_FOUND)
    private final String errorCode;

    // Status HTTP que será retornado (ex: 404, 400, etc)
    private final HttpStatus status;

    // Construtor da exceção
    public ApiException(String errorCode, String message, HttpStatus status) {

        // Chama o construtor da classe pai (RuntimeException)
        // passando a mensagem do erro
        super(message);

        // Define o código do erro
        this.errorCode = errorCode;

        // Define o status HTTP
        this.status = status;
    }

    // Retorna o código do erro
    public String getErrorCode() {
        return errorCode;
    }

    // Retorna o status HTTP
    public HttpStatus getStatus() {
        return status;
    }
}