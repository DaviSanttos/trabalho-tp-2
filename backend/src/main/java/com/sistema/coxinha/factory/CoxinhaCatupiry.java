package com.sistema.coxinha.factory;

public class CoxinhaCatupiry implements Coxinha {
    @Override
    public String getSabor() {
        return "CATUPIRY";
    }

    @Override
    public Double getPrecoBase() {
        return 6.0;
    }
}
