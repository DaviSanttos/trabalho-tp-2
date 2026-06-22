package com.sistema.coxinha.factory;

import org.springframework.stereotype.Component;

@Component
public class CoxinhaCatupiryFactory extends CoxinhaFactory {

    @Override
    public Coxinha criarCoxinha() {
        return new CoxinhaCatupiry();
    }
}
