package br.com.raizes.backend.infrastructure.repository;

import br.com.raizes.backend.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

// Interface usada para acessar os dados da tabela Produto no banco
// Mesmo sem métodos, o Spring já cria várias funções automaticamente
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Não tem métodos personalizados aqui
    // Mesmo assim já dá pra usar:
    // save -> salvar produto
    // findById -> buscar por ID
    // findAll -> listar todos
    // delete -> deletar

}