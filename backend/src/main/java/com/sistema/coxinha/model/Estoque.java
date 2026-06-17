package com.sistema.coxinha.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Estoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String produto;

    private Integer quantidade;

    public void adicionarQuantidade(Integer qtd) {
        if (this.quantidade == null) {
            this.quantidade = 0;
        }
        this.quantidade += qtd;
    }

    public void removerQuantidade(Integer qtd) {
        if (this.quantidade == null || this.quantidade < qtd) {
            throw new RuntimeException("Estoque insuficiente para " + this.produto);
        }
        this.quantidade -= qtd;
    }
}
