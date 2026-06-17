package com.sistema.coxinha.repository;

import com.sistema.coxinha.model.Estoque;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class EstoqueRepositoryTest {

    @Autowired
    private EstoqueRepository estoqueRepository;

    @Test
    void shouldSaveAndFindByProduto() {
        Estoque estoque = Estoque.builder()
                .produto("FRANGO")
                .quantidade(50)
                .build();

        estoqueRepository.save(estoque);

        Optional<Estoque> found = estoqueRepository.findByProduto("FRANGO");

        assertThat(found).isPresent();
        assertThat(found.get().getQuantidade()).isEqualTo(50);
    }
}
