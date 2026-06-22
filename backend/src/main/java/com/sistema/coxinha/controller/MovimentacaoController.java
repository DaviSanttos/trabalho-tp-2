package com.sistema.coxinha.controller;

import com.sistema.coxinha.dto.MovimentacaoResponseDTO;
import com.sistema.coxinha.model.Movimentacao;
import com.sistema.coxinha.repository.MovimentacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movimentacoes")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MovimentacaoController {

    private final MovimentacaoRepository movimentacaoRepository;

    @GetMapping
    public ResponseEntity<List<MovimentacaoResponseDTO>> listarTodos() {
        List<MovimentacaoResponseDTO> dtos = movimentacaoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    private MovimentacaoResponseDTO convertToDTO(Movimentacao m) {
        return MovimentacaoResponseDTO.builder()
                .id(m.getId())
                .data(m.getData())
                .tipo(m.getTipo())
                .valor(m.getValor())
                .descricao(m.getDescricao())
                .build();
    }
}
