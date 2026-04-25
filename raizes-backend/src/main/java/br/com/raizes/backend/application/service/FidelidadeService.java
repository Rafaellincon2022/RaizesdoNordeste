package br.com.raizes.backend.application.service;

import br.com.raizes.backend.api.dto.FidelidadeDtos;
import br.com.raizes.backend.api.exception.ApiException;
import br.com.raizes.backend.domain.entity.FidelidadeSaldo;
import br.com.raizes.backend.infrastructure.repository.FidelidadeSaldoRepository;
import br.com.raizes.backend.infrastructure.repository.UsuarioRepository;
import java.time.Instant;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// service responsável pelo sistema de fidelidade (pontos)
@Service
public class FidelidadeService {

private final FidelidadeSaldoRepository saldoRepository;
private final UsuarioRepository usuarioRepository;

// construtor para injeção de dependências
public FidelidadeService(FidelidadeSaldoRepository saldoRepository, UsuarioRepository usuarioRepository) {
    this.saldoRepository = saldoRepository;
    this.usuarioRepository = usuarioRepository;
}

// consulta o saldo de pontos do usuário
public FidelidadeDtos.SaldoResponse consultarSaldo(Long usuarioId) {

    // pega o saldo ou cria se não existir
    FidelidadeSaldo saldo = getOrCreateSaldo(usuarioId);

    // retorna os dados
    return new FidelidadeDtos.SaldoResponse(
            usuarioId,
            saldo.getPontos(),
            saldo.getAtualizadoEm().toString()
    );
}

// resgatar pontos (diminuir saldo)
@Transactional
public FidelidadeDtos.SaldoResponse resgatar(Long usuarioId, Integer pontos) {

    // pega saldo
    FidelidadeSaldo saldo = getOrCreateSaldo(usuarioId);

    // verifica se tem pontos suficientes
    if (saldo.getPontos() < pontos) {
        throw new ApiException(
                "PONTOS_INSUFICIENTES",
                "Saldo insuficiente para resgate.",
                HttpStatus.CONFLICT
        );
    }

    // subtrai os pontos
    saldo.setPontos(saldo.getPontos() - pontos);

    // atualiza data/hora
    saldo.setAtualizadoEm(Instant.now());

    // salva no banco
    saldo = saldoRepository.save(saldo);

    // retorna atualizado
    return new FidelidadeDtos.SaldoResponse(
            usuarioId,
            saldo.getPontos(),
            saldo.getAtualizadoEm().toString()
    );
}

// acumula pontos (somar)
@Transactional
public void acumular(Long usuarioId, int pontos) {

    // se for zero ou negativo, não faz nada
    if (pontos <= 0) {
        return;
    }

    // pega saldo
    FidelidadeSaldo saldo = getOrCreateSaldo(usuarioId);

    // soma os pontos
    saldo.setPontos(saldo.getPontos() + pontos);

    // atualiza data
    saldo.setAtualizadoEm(Instant.now());

    // salva
    saldoRepository.save(saldo);
}

// método para buscar ou criar saldo
private FidelidadeSaldo getOrCreateSaldo(Long usuarioId) {

    return saldoRepository.findByUsuarioId(usuarioId).orElseGet(() -> {

        // busca usuário
        var usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ApiException(
                        "USUARIO_NAO_ENCONTRADO",
                        "Usuario nao encontrado.",
                        HttpStatus.NOT_FOUND
                ));

        // cria novo saldo
        FidelidadeSaldo saldo = new FidelidadeSaldo();
        saldo.setUsuario(usuario);
        saldo.setPontos(0); // começa com zero
        saldo.setAtualizadoEm(Instant.now());

        // salva no banco
        return saldoRepository.save(saldo);
    });
}

}
