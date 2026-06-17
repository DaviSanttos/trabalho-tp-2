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
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Double saldo;

    public void adicionarSaldo(Double valor) {
        if (this.saldo == null) {
            this.saldo = 0.0;
        }
        this.saldo += valor;
    }

    public void debitarSaldo(Double valor) {
        if (this.saldo == null || this.saldo < valor) {
            throw new RuntimeException("Saldo insuficiente");
        }
        this.saldo -= valor;
    }
}
