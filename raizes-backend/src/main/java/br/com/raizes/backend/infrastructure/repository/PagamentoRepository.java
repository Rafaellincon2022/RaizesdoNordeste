package br.com.raizes.backend.infrastructure.repository;

import br.com.raizes.backend.domain.entity.Pagamento;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    Optional<Pagamento> findByPedidoId(Long pedidoId);
}
