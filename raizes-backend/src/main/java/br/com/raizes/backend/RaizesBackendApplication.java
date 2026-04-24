package br.com.raizes.backend; // tipo a pasta do projeto, organiza os arquivos

import org.springframework.boot.SpringApplication; // importa a função que vai rodar a aplicação
import org.springframework.boot.autoconfigure.SpringBootApplication; // importa a anotação principal do Spring Boot

@SpringBootApplication // fala pro Spring que essa é a aplicação principal, liga tudo automático
public class RaizesBackendApplication { // classe principal da aplicação
    
    public static void main(String[] args) { // o Java começa a rodar por aqui
        SpringApplication.run(RaizesBackendApplication.class, args); // aperta o botão start do Spring Boot
    }
}