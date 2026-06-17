package com.sistema.coxinha.state;

public class EstornadaState implements MovimentacaoState {
    @Override
    public String getStatus() {
        return "ESTORNADA";
    }

    @Override
    public void confirmar(MovimentacaoContext context) {
        throw new IllegalStateException("Não é possível confirmar um pedido estornado");
    }

    @Override
    public void estornar(MovimentacaoContext context) {
        throw new IllegalStateException("Pedido já foi estornado");
    }

    @Override
    public void cancelar(MovimentacaoContext context) {
        throw new IllegalStateException("Não é possível cancelar um pedido estornado");
    }
}
