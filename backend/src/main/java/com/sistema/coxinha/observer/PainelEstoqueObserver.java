package com.sistema.coxinha.observer;

import org.springframework.stereotype.Component;

@Component
public class PainelEstoqueObserver implements EstoqueObserver {
    @Override
    public void atualizar(String produto, Integer quantidade) {
        // Simular atualização de um painel em tempo real (ex: via WebSocket no futuro)
        System.out.println("[PAINEL] " + produto + ": " + quantidade + " unidades");
    }
}
