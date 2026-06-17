package com.sistema.coxinha.pattern;

import com.sistema.coxinha.factory.Coxinha;
import com.sistema.coxinha.factory.CoxinhaFactory;
import com.sistema.coxinha.factory.CoxinhaFrango;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CoxinhaFactoryTest {

    private final CoxinhaFactory factory = new CoxinhaFactory();

    @Test
    void shouldCreateCoxinhaFrango() {
        Coxinha coxinha = factory.criarCoxinha("FRANGO");
        assertThat(coxinha).isInstanceOf(CoxinhaFrango.class);
        assertThat(coxinha.getSabor()).isEqualTo("FRANGO");
    }
}
