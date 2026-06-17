package com.sistema.coxinha.pattern;

import com.sistema.coxinha.strategy.PrecoPadrao;
import com.sistema.coxinha.strategy.PrecoPromocional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CalculoPrecoStrategyTest {

    @Test
    void shouldCalculatePadrao() {
        PrecoPadrao strategy = new PrecoPadrao();
        assertThat(strategy.calcular(10.0)).isEqualTo(10.0);
    }

    @Test
    void shouldCalculatePromocional() {
        PrecoPromocional strategy = new PrecoPromocional();
        assertThat(strategy.calcular(10.0)).isEqualTo(9.0);
    }
}
