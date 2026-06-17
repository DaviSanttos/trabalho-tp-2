package com.sistema.coxinha.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovimentacaoResponseDTO {
    private Long id;
    private LocalDateTime data;
    private Double valorNota;
    private Double valorPedido;
    private String sabor;
    private String status;
    private Long clienteId;
}
