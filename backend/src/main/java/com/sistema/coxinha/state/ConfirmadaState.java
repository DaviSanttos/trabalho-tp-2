package com.sistema.coxinha.state;

public class ConfirmadaState implements MovimentacaoState {
    @Override
    public String getStatus() {
        return "CONFIRMADA";
    }

    @Override
    public void confirmar(MovimentacaoContext context) {
        throw new IllegalStateException("Pedido já está confirmado");
    }

    @Override
    public void estornar(MovimentacaoContext context) {
        context.setState(new EstornadaState());
    }

    @Override
    public void cancelar(MovimentacaoContext context) {
        throw new IllegalStateException("Não é possível cancelar um pedido já confirmado. Utilize o estorno.");
    }
}
