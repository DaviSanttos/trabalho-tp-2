package com.sistema.coxinha.pattern;

import com.sistema.coxinha.state.MovimentacaoContext;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MovimentacaoStateTest {

    @Test
    void shouldTransitionFromPendenteToConfirmada() {
        MovimentacaoContext context = new MovimentacaoContext();
        assertThat(context.getStatus()).isEqualTo("PENDENTE");

        context.confirmar();
        assertThat(context.getStatus()).isEqualTo("CONFIRMADA");
    }

    @Test
    void shouldTransitionFromConfirmadaToEstornada() {
        MovimentacaoContext context = new MovimentacaoContext("CONFIRMADA");
        context.estornar();
        assertThat(context.getStatus()).isEqualTo("ESTORNADA");
    }

    @Test
    void shouldThrowExceptionWhenInvalidTransition() {
        MovimentacaoContext context = new MovimentacaoContext("CONFIRMADA");
        assertThrows(IllegalStateException.class, context::confirmar);
    }
}
