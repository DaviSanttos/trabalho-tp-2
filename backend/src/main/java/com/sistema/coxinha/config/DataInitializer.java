package com.sistema.coxinha.config;

import com.sistema.coxinha.model.Cliente;
import com.sistema.coxinha.model.Estoque;
import com.sistema.coxinha.repository.ClienteRepository;
import com.sistema.coxinha.repository.EstoqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ClienteRepository clienteRepository;
    private final EstoqueRepository estoqueRepository;

    @Override
    public void run(String... args) throws Exception {
        if (clienteRepository.count() == 0) {
            clienteRepository.save(Cliente.builder()
                    .nome("Davi").email("davi@email.com").senha("123456").build());
            clienteRepository.save(Cliente.builder()
                    .nome("Maria").email("maria@email.com").senha("123456").build());
        }

        if (estoqueRepository.count() == 0) {
            estoqueRepository.save(Estoque.builder().produto("FRANGO").quantidade(10).build());
            estoqueRepository.save(Estoque.builder().produto("CATUPIRY").quantidade(10).build());
            estoqueRepository.save(Estoque.builder().produto("CALABRESA").quantidade(10).build());
        }
    }
}
