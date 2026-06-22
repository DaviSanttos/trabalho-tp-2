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
    private String tipo;
    private Double valor;
    private String descricao;
}
