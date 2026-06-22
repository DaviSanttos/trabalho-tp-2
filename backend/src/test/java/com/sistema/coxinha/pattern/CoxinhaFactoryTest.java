package com.sistema.coxinha.pattern;

import com.sistema.coxinha.factory.Coxinha;
import com.sistema.coxinha.factory.CoxinhaCalabresa;
import com.sistema.coxinha.factory.CoxinhaCalabresaFactory;
import com.sistema.coxinha.factory.CoxinhaCatupiry;
import com.sistema.coxinha.factory.CoxinhaCatupiryFactory;
import com.sistema.coxinha.factory.CoxinhaFrango;
import com.sistema.coxinha.factory.CoxinhaFrangoFactory;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CoxinhaFactoryTest {

    private final CoxinhaFrangoFactory frangoFactory = new CoxinhaFrangoFactory();
    private final CoxinhaCatupiryFactory catupiryFactory = new CoxinhaCatupiryFactory();
    private final CoxinhaCalabresaFactory calabresaFactory = new CoxinhaCalabresaFactory();

    @Test
    void shouldCreateCoxinhaFrango() {
        Coxinha coxinha = frangoFactory.criarCoxinha();
        assertThat(coxinha).isInstanceOf(CoxinhaFrango.class);
        assertThat(coxinha.getSabor()).isEqualTo("FRANGO");
    }

    @Test
    void shouldCreateCoxinhaCatupiry() {
        Coxinha coxinha = catupiryFactory.criarCoxinha();
        assertThat(coxinha).isInstanceOf(CoxinhaCatupiry.class);
        assertThat(coxinha.getSabor()).isEqualTo("CATUPIRY");
    }

    @Test
    void shouldCreateCoxinhaCalabresa() {
        Coxinha coxinha = calabresaFactory.criarCoxinha();
        assertThat(coxinha).isInstanceOf(CoxinhaCalabresa.class);
        assertThat(coxinha.getSabor()).isEqualTo("CALABRESA");
    }
}
