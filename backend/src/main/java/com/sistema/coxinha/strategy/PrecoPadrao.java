package com.sistema.coxinha.strategy;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class PrecoPadrao implements CalculoPrecoStrategy {
    @Override
    public Double calcular(Double precoBase) {
        return precoBase;
    }
}
