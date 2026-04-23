package br.com.raizes.backend.infrastructure.repository;

import br.com.raizes.backend.domain.entity.Pedido;
import br.com.raizes.backend.domain.enums.CanalPedido;
import br.com.raizes.backend.domain.enums.PedidoStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByCanalPedido(CanalPedido canalPedido);
    List<Pedido> findByStatus(PedidoStatus status);
    List<Pedido> findByCanalPedidoAndStatus(CanalPedido canalPedido, PedidoStatus status);
}
