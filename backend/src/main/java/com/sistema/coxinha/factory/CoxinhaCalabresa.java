package com.sistema.coxinha.factory;

public class CoxinhaCalabresa implements Coxinha {
    @Override
    public String getSabor() {
        return "CALABRESA";
    }

    @Override
    public Double getPrecoBase() {
        return 5.5;
    }
}
