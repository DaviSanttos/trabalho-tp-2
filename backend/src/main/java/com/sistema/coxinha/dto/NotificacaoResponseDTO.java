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
public class NotificacaoResponseDTO {
    private Long id;
    private String mensagem;
    private LocalDateTime data;
    private String tipo;
    private boolean lida;
}
