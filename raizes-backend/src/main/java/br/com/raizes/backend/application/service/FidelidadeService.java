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

@Service
public class FidelidadeService {
    private final FidelidadeSaldoRepository saldoRepository;
    private final UsuarioRepository usuarioRepository;

    public FidelidadeService(FidelidadeSaldoRepository saldoRepository, UsuarioRepository usuarioRepository) {
        this.saldoRepository = saldoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public FidelidadeDtos.SaldoResponse consultarSaldo(Long usuarioId) {
        FidelidadeSaldo saldo = getOrCreateSaldo(usuarioId);
        return new FidelidadeDtos.SaldoResponse(usuarioId, saldo.getPontos(), saldo.getAtualizadoEm().toString());
    }

    @Transactional
    public FidelidadeDtos.SaldoResponse resgatar(Long usuarioId, Integer pontos) {
        FidelidadeSaldo saldo = getOrCreateSaldo(usuarioId);
        if (saldo.getPontos() < pontos) {
            throw new ApiException("PONTOS_INSUFICIENTES", "Saldo insuficiente para resgate.", HttpStatus.CONFLICT);
        }
        saldo.setPontos(saldo.getPontos() - pontos);
        saldo.setAtualizadoEm(Instant.now());
        saldo = saldoRepository.save(saldo);
        return new FidelidadeDtos.SaldoResponse(usuarioId, saldo.getPontos(), saldo.getAtualizadoEm().toString());
    }

    @Transactional
    public void acumular(Long usuarioId, int pontos) {
        if (pontos <= 0) {
            return;
        }
        FidelidadeSaldo saldo = getOrCreateSaldo(usuarioId);
        saldo.setPontos(saldo.getPontos() + pontos);
        saldo.setAtualizadoEm(Instant.now());
        saldoRepository.save(saldo);
    }

    private FidelidadeSaldo getOrCreateSaldo(Long usuarioId) {
        return saldoRepository.findByUsuarioId(usuarioId).orElseGet(() -> {
            var usuario = usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new ApiException("USUARIO_NAO_ENCONTRADO", "Usuario nao encontrado.", HttpStatus.NOT_FOUND));
            FidelidadeSaldo saldo = new FidelidadeSaldo();
            saldo.setUsuario(usuario);
            saldo.setPontos(0);
            saldo.setAtualizadoEm(Instant.now());
            return saldoRepository.save(saldo);
        });
    }
}
