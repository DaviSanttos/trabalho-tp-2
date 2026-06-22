package com.sistema.coxinha.state;

public class ConfirmadaState implements PedidoState {
    @Override
    public String getStatus() {
        return "CONFIRMADA";
    }

    @Override
    public void confirmar(PedidoContext context) {
        throw new IllegalStateException("Pedido já está confirmado");
    }

    @Override
    public void estornar(PedidoContext context) {
        context.setState(new EstornadaState());
    }

    @Override
    public void cancelar(PedidoContext context) {
        throw new IllegalStateException("Não é possível cancelar um pedido já confirmado. Utilize o estorno.");
    }
}
