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
public class PedidoResponseDTO {
    private Long id;
    private Long clienteId;
    private String nomeCliente;
    private LocalDateTime data;
    private String sabor;
    private Integer quantidade;
    private Double valorTotal;
    private String status;
}
