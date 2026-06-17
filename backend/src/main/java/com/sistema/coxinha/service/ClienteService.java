package com.sistema.coxinha.service;

import com.sistema.coxinha.model.Cliente;
import com.sistema.coxinha.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Cliente buscarPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    @Transactional
    public void adicionarSaldo(Long id, Double valor) {
        Cliente cliente = buscarPorId(id);
        cliente.adicionarSaldo(valor);
        clienteRepository.save(cliente);
    }

    @Transactional
    public void debitarSaldo(Long id, Double valor) {
        Cliente cliente = buscarPorId(id);
        cliente.debitarSaldo(valor);
        clienteRepository.save(cliente);
    }
}
