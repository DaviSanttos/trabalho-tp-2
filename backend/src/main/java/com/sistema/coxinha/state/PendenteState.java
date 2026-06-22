package com.sistema.coxinha.state;

public class PendenteState implements PedidoState {
    @Override
    public String getStatus() {
        return "PENDENTE";
    }

    @Override
    public void confirmar(PedidoContext context) {
        context.setState(new ConfirmadaState());
    }

    @Override
    public void estornar(PedidoContext context) {
        throw new IllegalStateException("Não é possível estornar um pedido pendente");
    }

    @Override
    public void cancelar(PedidoContext context) {
        context.setState(new CanceladaState());
    }
}
