package br.com.raizes.backend.infrastructure.repository;

import br.com.raizes.backend.domain.entity.FidelidadeSaldo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FidelidadeSaldoRepository extends JpaRepository<FidelidadeSaldo, Long> {
    Optional<FidelidadeSaldo> findByUsuarioId(Long usuarioId);
}
