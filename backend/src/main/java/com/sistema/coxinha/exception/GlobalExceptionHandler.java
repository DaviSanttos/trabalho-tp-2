package com.sistema.coxinha.exception;

import com.sistema.coxinha.dto.MensagemResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<MensagemResponseDTO> handleIllegalStateException(IllegalStateException e) {
        return ResponseEntity.badRequest().body(MensagemResponseDTO.builder()
                .sucesso(false)
                .mensagem(e.getMessage())
                .build());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<MensagemResponseDTO> handleRuntimeException(RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().body(MensagemResponseDTO.builder()
                .sucesso(false)
                .mensagem(e.getMessage())
                .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MensagemResponseDTO> handleException(Exception e) {
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(MensagemResponseDTO.builder()
                .sucesso(false)
                .mensagem("Erro interno: " + e.getMessage())
                .build());
    }
}
