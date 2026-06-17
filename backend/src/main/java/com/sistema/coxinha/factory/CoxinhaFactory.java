package com.sistema.coxinha.factory;

import org.springframework.stereotype.Component;

@Component
public class CoxinhaFactory {

    public Coxinha criarCoxinha(String sabor) {
        if (sabor == null) {
            return null;
        }
        switch (sabor.toUpperCase()) {
            case "FRANGO":
                return new CoxinhaFrango();
            case "CATUPIRY":
                return new CoxinhaCatupiry();
            case "CALABRESA":
                return new CoxinhaCalabresa();
            default:
                throw new IllegalArgumentException("Sabor desconhecido: " + sabor);
        }
    }
}
