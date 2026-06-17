package com.sistema.coxinha.observer;

import org.springframework.stereotype.Component;

@Component
public class LogEstoqueObserver implements EstoqueObserver {
    @Override
    public void atualizar(String produto, Integer quantidade) {
        System.out.println("[LOG] Estoque atualizado: " + produto + " - Nova quantidade: " + quantidade);
        if (quantidade < 5) {
            System.out.println("[ALERTA] Estoque baixo para: " + produto);
        }
    }
}
