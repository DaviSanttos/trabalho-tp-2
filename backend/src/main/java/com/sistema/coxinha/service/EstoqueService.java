package com.sistema.coxinha.service;

import com.sistema.coxinha.model.Estoque;
import com.sistema.coxinha.observer.EstoqueObserver;
import com.sistema.coxinha.repository.EstoqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstoqueService {

    private final EstoqueRepository estoqueRepository;
    private final List<EstoqueObserver> observers;

    public List<Estoque> listarEstoque() {
        return estoqueRepository.findAll();
    }

    @Transactional
    public void baixarEstoque(String sabor, Integer quantidade) {
        Estoque estoque = estoqueRepository.findByProduto(sabor.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Produto não encontrado no estoque: " + sabor));
        
        estoque.removerQuantidade(quantidade);
        estoqueRepository.save(estoque);
        notificarObservers(estoque);
    }

    @Transactional
    public void reporEstoque(String sabor, Integer quantidade) {
        Estoque estoque = estoqueRepository.findByProduto(sabor.toUpperCase())
                .orElseGet(() -> Estoque.builder().produto(sabor.toUpperCase()).quantidade(0).build());
        
        estoque.adicionarQuantidade(quantidade);
        estoqueRepository.save(estoque);
        notificarObservers(estoque);
    }

    private void notificarObservers(Estoque estoque) {
        observers.forEach(o -> o.atualizar(estoque.getProduto(), estoque.getQuantidade()));
    }
}
