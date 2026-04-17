package com.uninter.lanchonete.repository;

import com.uninter.lanchonete.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
