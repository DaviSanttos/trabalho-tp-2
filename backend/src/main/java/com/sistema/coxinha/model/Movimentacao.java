package com.sistema.coxinha.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime data;

    private Double valorNota;

    private Double valorPedido;

    private String sabor;

    private String status;

    private Long clienteId; // Relacionamento simplificado para DTOs

    // Construtor manual para evitar problemas com Lombok @Builder em tempo de execução
    public Movimentacao(LocalDateTime data, Double valorNota, Double valorPedido, String sabor, String status, Long clienteId) {
        this.data = data;
        this.valorNota = valorNota;
        this.valorPedido = valorPedido;
        this.sabor = sabor;
        this.status = status;
        this.clienteId = clienteId;
    }
}
