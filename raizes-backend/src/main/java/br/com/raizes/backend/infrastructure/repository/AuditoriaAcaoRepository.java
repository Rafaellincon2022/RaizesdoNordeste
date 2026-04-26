package br.com.raizes.backend.infrastructure.repository;

import br.com.raizes.backend.domain.entity.AuditoriaAcao;
import org.springframework.data.jpa.repository.JpaRepository;

// essa interface serve para acessar o banco de dados da entidade AuditoriaAcao
public interface AuditoriaAcaoRepository extends JpaRepository<AuditoriaAcao, Long> {

    // aqui eu não precisei criar nenhum método
    // o Spring já cria vários automaticamente, como:
    // save() -> salvar no banco
    // findById() -> buscar por id
    // findAll() -> listar tudo
    // deleteById() -> deletar

}