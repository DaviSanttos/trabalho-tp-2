package com.sistema.coxinha.service;

import com.sistema.coxinha.model.Cliente;
import com.sistema.coxinha.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Cliente> login(String email, String senha) {
        return clienteRepository.findByEmail(email)
                .filter(c -> c.getSenha().equals(senha));
    }
}
