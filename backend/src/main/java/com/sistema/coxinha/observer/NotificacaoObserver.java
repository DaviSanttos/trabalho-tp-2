package com.sistema.coxinha.observer;

import com.sistema.coxinha.model.Notificacao;
import com.sistema.coxinha.repository.NotificacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class NotificacaoObserver implements EstoqueObserver {

    private final NotificacaoRepository notificacaoRepository;

    @Override
    public void atualizar(String produto, Integer quantidade) {
        String mensagem = "Estoque atualizado: " + produto + " - " + quantidade + " unidades";
        String tipo = quantidade < 5 ? "ESTOQUE_BAIXO" : "ESTOQUE_ALTERADO";

        notificacaoRepository.save(Notificacao.builder()
                .mensagem(mensagem)
                .data(LocalDateTime.now())
                .tipo(tipo)
                .lida(false)
                .build());
    }
}
