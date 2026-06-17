package com.sistema.coxinha.state;

public interface MovimentacaoState {
    String getStatus();
    void confirmar(MovimentacaoContext context);
    void estornar(MovimentacaoContext context);
    void cancelar(MovimentacaoContext context);
}
