package com.sistema.coxinha.repository;

import com.sistema.coxinha.model.Cliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void shouldSaveAndFindCliente() {
        Cliente cliente = Cliente.builder()
                .nome("Davi")
                .email("davi@email.com")
                .senha("123456")
                .build();

        Cliente saved = clienteRepository.save(cliente);

        assertThat(saved.getId()).isNotNull();
        assertThat(clienteRepository.findById(saved.getId())).isPresent();
    }

    @Test
    void shouldFindByEmail() {
        Cliente cliente = Cliente.builder()
                .nome("Maria")
                .email("maria@email.com")
                .senha("123456")
                .build();

        clienteRepository.save(cliente);

        assertThat(clienteRepository.findByEmail("maria@email.com")).isPresent();
        assertThat(clienteRepository.findByEmail("naoexiste@email.com")).isEmpty();
    }
}
