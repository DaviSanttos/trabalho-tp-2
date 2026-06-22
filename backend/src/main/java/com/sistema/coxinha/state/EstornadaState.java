package com.sistema.coxinha.state;

public class EstornadaState implements PedidoState {
    @Override
    public String getStatus() {
        return "ESTORNADA";
    }

    @Override
    public void confirmar(PedidoContext context) {
        throw new IllegalStateException("Não é possível confirmar um pedido estornado");
    }

    @Override
    public void estornar(PedidoContext context) {
        throw new IllegalStateException("Pedido já foi estornado");
    }

    @Override
    public void cancelar(PedidoContext context) {
        throw new IllegalStateException("Não é possível cancelar um pedido estornado");
    }
}
