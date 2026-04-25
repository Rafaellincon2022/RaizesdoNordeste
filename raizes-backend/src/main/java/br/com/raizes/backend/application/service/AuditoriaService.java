package br.com.raizes.backend.application.service;

import br.com.raizes.backend.domain.entity.AuditoriaAcao;
import br.com.raizes.backend.infrastructure.repository.AuditoriaAcaoRepository;
import java.time.Instant;
import org.springframework.stereotype.Service;

// Service responsável por registrar logs de ações no sistema
@Service
public class AuditoriaService {

    // Repository usado para salvar os logs no banco
    private final AuditoriaAcaoRepository repository;

    // Construtor com injeção de dependência
    public AuditoriaService(AuditoriaAcaoRepository repository) {
        this.repository = repository;
    }

    // Método para registrar uma ação no sistema
    public void registrar(
            String actorEmail, // quem fez a ação
            String acao,       // ação realizada (ex: CRIAR_PEDIDO)
            String entidade,   // entidade afetada (ex: PEDIDO)
            Long entidadeId,   // ID da entidade
            String detalhes    // informações adicionais
    ) {

        // Cria um novo objeto de auditoria
        AuditoriaAcao log = new AuditoriaAcao();

        // Define o email do usuário que fez a ação
        log.setActorEmail(actorEmail);

        // Define qual ação foi realizada
        log.setAcao(acao);

        // Define a entidade afetada
        log.setEntidade(entidade);

        // Define o ID da entidade
        log.setEntidadeId(entidadeId);

        // Define detalhes extras
        log.setDetalhes(detalhes);

        // Define a data/hora atual
        log.setCriadoEm(Instant.now());

        // Salva no banco de dados
        repository.save(log);
    }
}