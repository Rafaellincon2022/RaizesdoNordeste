package com.uninter.lanchonete.repository;

import com.uninter.lanchonete.model.CanalPedido;
import com.uninter.lanchonete.model.Pedido;
import com.uninter.lanchonete.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByCanalPedido(CanalPedido canalPedido);
    List<Pedido> findByCliente(Usuario cliente);
}
