package com.sistema.coxinha.pattern;

import com.sistema.coxinha.state.PedidoContext;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MovimentacaoStateTest {

    @Test
    void shouldTransitionFromPendenteToConfirmada() {
        PedidoContext context = new PedidoContext();
        assertThat(context.getStatus()).isEqualTo("PENDENTE");

        context.confirmar();
        assertThat(context.getStatus()).isEqualTo("CONFIRMADA");
    }

    @Test
    void shouldTransitionFromConfirmadaToEstornada() {
        PedidoContext context = new PedidoContext("CONFIRMADA");
        context.estornar();
        assertThat(context.getStatus()).isEqualTo("ESTORNADA");
    }

    @Test
    void shouldThrowExceptionWhenInvalidTransition() {
        PedidoContext context = new PedidoContext("CONFIRMADA");
        assertThrows(IllegalStateException.class, context::confirmar);
    }
}
