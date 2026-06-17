package com.sistema.coxinha.state;

public class PendenteState implements MovimentacaoState {
    @Override
    public String getStatus() {
        return "PENDENTE";
    }

    @Override
    public void confirmar(MovimentacaoContext context) {
        context.setState(new ConfirmadaState());
    }

    @Override
    public void estornar(MovimentacaoContext context) {
        throw new IllegalStateException("Não é possível estornar um pedido pendente");
    }

    @Override
    public void cancelar(MovimentacaoContext context) {
        context.setState(new CanceladaState());
    }
}
