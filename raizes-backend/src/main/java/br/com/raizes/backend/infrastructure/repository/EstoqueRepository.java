package br.com.raizes.backend.infrastructure.repository;

import br.com.raizes.backend.domain.entity.Estoque;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    Optional<Estoque> findByUnidadeIdAndProdutoId(Long unidadeId, Long produtoId);
    List<Estoque> findByUnidadeId(Long unidadeId);
}
