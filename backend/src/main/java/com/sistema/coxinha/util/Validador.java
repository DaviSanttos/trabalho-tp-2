package com.sistema.coxinha.util;

public class Validador {

    public static void validarQuantidade(Integer quantidade) {
        if (quantidade == null || quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
    }

    public static void validarSabor(String sabor) {
        if (sabor == null || sabor.trim().isEmpty()) {
            throw new IllegalArgumentException("Sabor não pode ser vazio");
        }
    }
}
