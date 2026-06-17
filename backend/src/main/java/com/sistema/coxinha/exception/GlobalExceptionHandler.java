package com.sistema.coxinha.exception;

import com.sistema.coxinha.dto.PedidoResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<PedidoResponseDTO> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.badRequest().body(PedidoResponseDTO.builder()
                .sucesso(false)
                .mensagem(e.getMessage())
                .build());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<PedidoResponseDTO> handleIllegalStateException(IllegalStateException e) {
        return ResponseEntity.badRequest().body(PedidoResponseDTO.builder()
                .sucesso(false)
                .mensagem(e.getMessage())
                .build());
    }
}
