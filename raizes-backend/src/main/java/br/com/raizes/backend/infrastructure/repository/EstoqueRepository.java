package br.com.raizes.backend.infrastructure.repository;

import br.com.raizes.backend.domain.entity.Estoque;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

// repositório para acessar dados da tabela de estoque
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {

    // busca um estoque específico baseado na unidade e no produto
    // pode não encontrar, por isso usei Optional
    Optional<Estoque> findByUnidadeIdAndProdutoId(Long unidadeId, Long produtoId);

    // busca todos os estoques de uma unidade específica
    // retorna uma lista porque pode ter vários produtos na mesma unidade
    List<Estoque> findByUnidadeId(Long unidadeId);
}