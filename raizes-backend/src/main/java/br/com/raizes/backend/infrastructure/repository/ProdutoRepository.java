package br.com.raizes.backend.infrastructure.repository;

import br.com.raizes.backend.domain.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
