package br.com.raizes.backend.infrastructure.repository;

import br.com.raizes.backend.domain.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

// Interface responsável por acessar os dados da tabela Usuario
// O Spring já cria métodos básicos automaticamente (CRUD)
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método para buscar um usuário pelo email
    // Muito usado em login/autenticação
    // Retorna Optional porque pode não existir usuário com esse email
    Optional<Usuario> findByEmail(String email);
}