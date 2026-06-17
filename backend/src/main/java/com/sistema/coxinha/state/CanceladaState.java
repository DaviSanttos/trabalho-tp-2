package com.sistema.coxinha.state;

public class CanceladaState implements MovimentacaoState {
    @Override
    public String getStatus() {
        return "CANCELADA";
    }

    @Override
    public void confirmar(MovimentacaoContext context) {
        throw new IllegalStateException("Não é possível confirmar um pedido cancelado");
    }

    @Override
    public void estornar(MovimentacaoContext context) {
        throw new IllegalStateException("Não é possível estornar um pedido cancelado");
    }

    @Override
    public void cancelar(MovimentacaoContext context) {
        throw new IllegalStateException("Pedido já foi cancelado");
    }
}
