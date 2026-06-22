package com.sistema.coxinha.service;

import com.sistema.coxinha.model.Estoque;
import com.sistema.coxinha.observer.EstoquePublisher;
import com.sistema.coxinha.repository.EstoqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final EstoquePublisher publisher;

    public List<Estoque> listarEstoque() {
        return estoqueRepository.findAll();
    }

    @Transactional
    public void baixarEstoque(String sabor, Integer quantidade) {
        Estoque estoque = estoqueRepository.findByProduto(sabor.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado no estoque: " + sabor));
        
        estoque.removerQuantidade(quantidade);
        estoqueRepository.save(estoque);
        publisher.notifyObservers(estoque.getProduto(), estoque.getQuantidade());
    }

    @Transactional
    public void reporEstoque(String sabor, Integer quantidade) {
        Estoque estoque = estoqueRepository.findByProduto(sabor.toUpperCase())
                .orElseGet(() -> Estoque.builder().produto(sabor.toUpperCase()).quantidade(0).build());
        
        estoque.adicionarQuantidade(quantidade);
        estoqueRepository.save(estoque);
        publisher.notifyObservers(estoque.getProduto(), estoque.getQuantidade());
    }
}
