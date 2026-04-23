package br.com.raizes.backend.application.service;

import br.com.raizes.backend.domain.entity.AuditoriaAcao;
import br.com.raizes.backend.infrastructure.repository.AuditoriaAcaoRepository;
import java.time.Instant;
import org.springframework.stereotype.Service;

@Service
public class AuditoriaService {
    private final AuditoriaAcaoRepository repository;

    public AuditoriaService(AuditoriaAcaoRepository repository) {
        this.repository = repository;
    }

    public void registrar(String actorEmail, String acao, String entidade, Long entidadeId, String detalhes) {
        AuditoriaAcao log = new AuditoriaAcao();
        log.setActorEmail(actorEmail);
        log.setAcao(acao);
        log.setEntidade(entidade);
        log.setEntidadeId(entidadeId);
        log.setDetalhes(detalhes);
        log.setCriadoEm(Instant.now());
        repository.save(log);
    }
}
