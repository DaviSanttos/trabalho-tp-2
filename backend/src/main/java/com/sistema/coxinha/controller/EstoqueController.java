package com.sistema.coxinha.controller;

import com.sistema.coxinha.dto.MensagemResponseDTO;
import com.sistema.coxinha.model.Estoque;
import com.sistema.coxinha.service.EstoqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/estoque")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EstoqueController {

    private final EstoqueService estoqueService;

    @GetMapping
    public ResponseEntity<List<Estoque>> listarEstoque() {
        return ResponseEntity.ok(estoqueService.listarEstoque());
    }

    @PostMapping("/repor")
    public ResponseEntity<MensagemResponseDTO> reporEstoque(@RequestBody Map<String, Object> body) {
        String sabor = (String) body.get("sabor");
        Integer quantidade = (Integer) body.get("quantidade");
        estoqueService.reporEstoque(sabor, quantidade);
        return ResponseEntity.ok(MensagemResponseDTO.builder()
                .sucesso(true)
                .mensagem("Estoque reposto com sucesso")
                .build());
    }
}
