package com.sistema.coxinha.factory;

import org.springframework.stereotype.Component;

@Component
public class CoxinhaCalabresaFactory extends CoxinhaFactory {

    @Override
    public Coxinha criarCoxinha() {
        return new CoxinhaCalabresa();
    }
}
