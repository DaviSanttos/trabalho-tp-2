package com.sistema.coxinha.state;

public class CanceladaState implements PedidoState {
    @Override
    public String getStatus() {
        return "CANCELADA";
    }

    @Override
    public void confirmar(PedidoContext context) {
        throw new IllegalStateException("Não é possível confirmar um pedido cancelado");
    }

    @Override
    public void estornar(PedidoContext context) {
        throw new IllegalStateException("Não é possível estornar um pedido cancelado");
    }

    @Override
    public void cancelar(PedidoContext context) {
        throw new IllegalStateException("Pedido já foi cancelado");
    }
}
