package br.com.raizes.backend.infrastructure.repository;

import br.com.raizes.backend.domain.entity.FidelidadeSaldo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

// Essa interface serve para acessar os dados da tabela FidelidadeSaldo no banco
// O Spring cria automaticamente os métodos básicos (salvar, buscar, deletar, etc)
public interface FidelidadeSaldoRepository extends JpaRepository<FidelidadeSaldo, Long> {

    // Esse método busca um saldo de fidelidade pelo ID do usuário
    // O nome do método é importante, porque o Spring entende automaticamente
    // "findByUsuarioId" = buscar pelo campo usuarioId
    // Pode retornar vazio, por isso usamos Optional
    Optional<FidelidadeSaldo> findByUsuarioId(Long usuarioId);
}