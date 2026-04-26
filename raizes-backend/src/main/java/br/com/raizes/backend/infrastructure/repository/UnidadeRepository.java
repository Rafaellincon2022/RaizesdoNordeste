package br.com.raizes.backend.infrastructure.repository;

import br.com.raizes.backend.domain.entity.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;

// Interface responsável por acessar os dados da tabela Unidade
// O Spring cria automaticamente os métodos básicos (CRUD)
public interface UnidadeRepository extends JpaRepository<Unidade, Long> {

    // Não tem métodos personalizados aqui
    // Mesmo assim já podemos:
    // salvar (save)
    // buscar por ID (findById)
    // listar tudo (findAll)
    // deletar (delete)

}