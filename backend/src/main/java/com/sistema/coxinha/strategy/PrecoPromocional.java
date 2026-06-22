package com.sistema.coxinha.strategy;

import org.springframework.stereotype.Component;

@Component
public class PrecoPromocional implements CalculoPrecoStrategy {
    @Override
    public Double calcular(Double precoBase) {
        return precoBase * 0.9;
    }
}
