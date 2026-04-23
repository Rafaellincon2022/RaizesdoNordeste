package br.com.raizes.backend.infrastructure.repository;

import br.com.raizes.backend.domain.entity.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnidadeRepository extends JpaRepository<Unidade, Long> {
}
