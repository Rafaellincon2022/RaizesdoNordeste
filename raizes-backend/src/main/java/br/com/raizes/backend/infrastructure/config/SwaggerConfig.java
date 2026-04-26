package br.com.raizes.backend.infrastructure.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

// essa anotação fala pro Spring que essa classe é de configuração
@Configuration

// aqui estou definindo algumas informações da API que aparecem no Swagger
@OpenAPIDefinition(
    info = @Info(
        title = "API Raízes do Nordeste", // nome da API
        version = "v1", // versão
        description = "Documentação oficial dos endpoints do sistema multicanal." // descrição
    )
)

// aqui estou configurando a autenticação usando JWT no Swagger
@SecurityScheme(
    name = "bearerAuth", // nome da autenticação
    type = SecuritySchemeType.HTTP, // tipo HTTP
    bearerFormat = "JWT", // formato do token
    scheme = "bearer" // esquema bearer (Authorization: Bearer token)
)
public class SwaggerConfig {

    // essa classe não precisa de nada dentro
    // só as anotações já fazem tudo funcionar

}