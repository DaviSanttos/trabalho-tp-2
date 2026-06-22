package com.sistema.coxinha.state;

public interface PedidoState {
    String getStatus();
    void confirmar(PedidoContext context);
    void estornar(PedidoContext context);
    void cancelar(PedidoContext context);
}
