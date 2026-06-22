package com.sistema.coxinha.controller;

import com.sistema.coxinha.dto.NotificacaoResponseDTO;
import com.sistema.coxinha.model.Notificacao;
import com.sistema.coxinha.repository.NotificacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificacoes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class NotificacaoController {

    private final NotificacaoRepository notificacaoRepository;

    @GetMapping
    public ResponseEntity<List<NotificacaoResponseDTO>> listarTodas() {
        List<NotificacaoResponseDTO> dtos = notificacaoRepository.findAllByOrderByDataDesc().stream()
                .map(this::convertToDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/nao-lidas")
    public ResponseEntity<List<NotificacaoResponseDTO>> listarNaoLidas() {
        List<NotificacaoResponseDTO> dtos = notificacaoRepository.findByLidaFalseOrderByDataDesc().stream()
                .map(this::convertToDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/contagem")
    public ResponseEntity<Long> contarNaoLidas() {
        return ResponseEntity.ok(notificacaoRepository.countByLidaFalse());
    }

    @PostMapping("/{id}/marcar-lida")
    public ResponseEntity<Void> marcarLida(@PathVariable Long id) {
        notificacaoRepository.findById(id).ifPresent(n -> {
            n.setLida(true);
            notificacaoRepository.save(n);
        });
        return ResponseEntity.ok().build();
    }

    @PostMapping("/marcar-todas-lidas")
    public ResponseEntity<Void> marcarTodasLidas() {
        List<Notificacao> naoLidas = notificacaoRepository.findByLidaFalseOrderByDataDesc();
        naoLidas.forEach(n -> n.setLida(true));
        notificacaoRepository.saveAll(naoLidas);
        return ResponseEntity.ok().build();
    }

    private NotificacaoResponseDTO convertToDTO(Notificacao n) {
        return NotificacaoResponseDTO.builder()
                .id(n.getId())
                .mensagem(n.getMensagem())
                .data(n.getData())
                .tipo(n.getTipo())
                .lida(n.isLida())
                .build();
    }
}
