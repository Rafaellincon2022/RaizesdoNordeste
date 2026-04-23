package br.com.raizes.backend.infrastructure.repository;

import br.com.raizes.backend.domain.entity.AuditoriaAcao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditoriaAcaoRepository extends JpaRepository<AuditoriaAcao, Long> {
}
