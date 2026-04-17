package com.uninter.lanchonete.repository;

import com.uninter.lanchonete.model.Estoque;
import com.uninter.lanchonete.model.Produto;
import com.uninter.lanchonete.model.Unidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    Optional<Estoque> findByProdutoAndUnidade(Produto produto, Unidade unidade);
    List<Estoque> findByUnidade(Unidade unidade);
}
