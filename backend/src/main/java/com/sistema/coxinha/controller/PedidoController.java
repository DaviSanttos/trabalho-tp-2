package com.sistema.coxinha.controller;

import com.sistema.coxinha.dto.MensagemResponseDTO;
import com.sistema.coxinha.dto.PedidoRequestDTO;
import com.sistema.coxinha.dto.PedidoResponseDTO;
import com.sistema.coxinha.model.Pedido;
import com.sistema.coxinha.service.ClienteService;
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
    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<MensagemResponseDTO> criarPedido(@RequestBody PedidoRequestDTO request) {
        pedidoService.realizarPedido(
                request.getClienteId(),
                request.getSabor(),
                request.getQuantidade(),
                request.isUsarCupom()
        );
        return ResponseEntity.ok(MensagemResponseDTO.builder()
                .sucesso(true)
                .mensagem("Pedido realizado com sucesso")
                .build());
    }

    @PostMapping("/{id}/estorno")
    public ResponseEntity<MensagemResponseDTO> estornarPedido(@PathVariable Long id) {
        pedidoService.estornarPedido(id);
        return ResponseEntity.ok(MensagemResponseDTO.builder()
                .sucesso(true)
                .mensagem("Estorno realizado com sucesso")
                .build());
    }

    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarHistorico(
            @RequestParam(required = false) Long clienteId) {
        List<Pedido> pedidos = (clienteId != null)
                ? pedidoService.listarPorCliente(clienteId)
                : pedidoService.listarHistorico();

        List<PedidoResponseDTO> dtos = pedidos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    private PedidoResponseDTO convertToDTO(Pedido p) {
        String nomeCliente = "";
        try {
            nomeCliente = clienteService.buscarPorId(p.getClienteId()).getNome();
        } catch (Exception e) {
            nomeCliente = "Desconhecido";
        }
        return PedidoResponseDTO.builder()
                .id(p.getId())
                .clienteId(p.getClienteId())
                .nomeCliente(nomeCliente)
                .data(p.getData())
                .sabor(p.getSabor())
                .quantidade(p.getQuantidade())
                .valorTotal(p.getValorTotal())
                .status(p.getStatus())
                .build();
    }
}
