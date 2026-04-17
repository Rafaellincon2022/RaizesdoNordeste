package com.uninter.lanchonete.repository;

import com.uninter.lanchonete.model.Fidelidade;
import com.uninter.lanchonete.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FidelidadeRepository extends JpaRepository<Fidelidade, Long> {
    Optional<Fidelidade> findByCliente(Usuario cliente);
}
