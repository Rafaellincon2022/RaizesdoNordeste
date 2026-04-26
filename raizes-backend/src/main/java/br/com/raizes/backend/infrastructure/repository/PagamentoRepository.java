package br.com.raizes.backend.infrastructure.repository;

import br.com.raizes.backend.domain.entity.Pagamento;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

// Interface usada para acessar a tabela de Pagamento no banco
// O Spring já cria vários métodos automaticamente (save, findById, delete, etc)
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    // Esse método busca um pagamento pelo ID do pedido
    // O Spring entende o nome "findByPedidoId" e cria a query automaticamente
    // Retorna Optional porque pode não existir pagamento para esse pedido
    Optional<Pagamento> findByPedidoId(Long pedidoId);
}