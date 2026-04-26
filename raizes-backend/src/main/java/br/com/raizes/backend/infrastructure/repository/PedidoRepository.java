package br.com.raizes.backend.infrastructure.repository;

import br.com.raizes.backend.domain.entity.Pedido;
import br.com.raizes.backend.domain.enums.CanalPedido;
import br.com.raizes.backend.domain.enums.PedidoStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

// Interface responsável por acessar os dados de Pedido no banco
// O Spring já cria vários métodos automaticamente (save, findAll, findById, etc)
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Busca pedidos pelo canal (ex: APP, SITE, LOJA)
    // Retorna uma lista porque podem existir vários pedidos no mesmo canal
    List<Pedido> findByCanalPedido(CanalPedido canalPedido);

    // Busca pedidos pelo status (ex: PENDENTE, PAGO, CANCELADO)
    // Também retorna lista porque vários pedidos podem ter o mesmo status
    List<Pedido> findByStatus(PedidoStatus status);

    // Busca pedidos usando dois filtros ao mesmo tempo:
    // canal + status
    // Exemplo: pedidos do APP que estão PENDENTES
    List<Pedido> findByCanalPedidoAndStatus(CanalPedido canalPedido, PedidoStatus status);
}