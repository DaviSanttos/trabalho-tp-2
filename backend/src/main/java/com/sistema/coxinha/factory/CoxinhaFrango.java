package com.sistema.coxinha.factory;

public class CoxinhaFrango implements Coxinha {
    @Override
    public String getSabor() {
        return "FRANGO";
    }

    @Override
    public Double getPrecoBase() {
        return 5.0;
    }
}
