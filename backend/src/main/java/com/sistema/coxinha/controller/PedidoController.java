package com.sistema.coxinha.controller;

import com.sistema.coxinha.dto.MovimentacaoResponseDTO;
import com.sistema.coxinha.dto.PedidoRequestDTO;
import com.sistema.coxinha.dto.PedidoResponseDTO;
import com.sistema.coxinha.model.Movimentacao;
import com.sistema.coxinha.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PedidoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(@RequestBody PedidoRequestDTO request) {
        pedidoService.realizarPedido(request.getClienteId(), request.getSabor(), request.getValorNota());
        return ResponseEntity.ok(PedidoResponseDTO.builder()
                .sucesso(true)
                .mensagem("Pedido realizado com sucesso")
                .build());
    }

    @PostMapping("/{id}/estorno")
    public ResponseEntity<PedidoResponseDTO> estornarPedido(@PathVariable Long id) {
        pedidoService.estornarPedido(id);
        return ResponseEntity.ok(PedidoResponseDTO.builder()
                .sucesso(true)
                .mensagem("Estorno realizado com sucesso")
                .build());
    }

    @GetMapping
    public ResponseEntity<List<MovimentacaoResponseDTO>> listarHistorico() {
        List<MovimentacaoResponseDTO> dtos = pedidoService.listarHistorico().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    private MovimentacaoResponseDTO convertToDTO(Movimentacao m) {
        return MovimentacaoResponseDTO.builder()
                .id(m.getId())
                .data(m.getData())
                .valorNota(m.getValorNota())
                .valorPedido(m.getValorPedido())
                .sabor(m.getSabor())
                .status(m.getStatus())
                .clienteId(m.getClienteId())
                .build();
    }
}
