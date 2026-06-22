package com.sistema.coxinha.command;

import com.sistema.coxinha.model.Movimentacao;
import com.sistema.coxinha.model.Pedido;
import com.sistema.coxinha.repository.MovimentacaoRepository;
import com.sistema.coxinha.repository.PedidoRepository;
import com.sistema.coxinha.service.EstoqueService;
import com.sistema.coxinha.state.PedidoContext;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class EstornarPedidoCommand implements Command {

    private final Long pedidoId;
    private final EstoqueService estoqueService;
    private final PedidoRepository pedidoRepository;
    private final MovimentacaoRepository movimentacaoRepository;

    @Override
    public void executar() {
        Pedido pedido = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        if ("ESTORNADA".equals(pedido.getStatus())) {
            throw new RuntimeException("Este pedido já foi estornado");
        }

        PedidoContext stateContext = new PedidoContext(pedido.getStatus());
        stateContext.estornar();

        estoqueService.reporEstoque(pedido.getSabor(), pedido.getQuantidade());

        pedido.setStatus(stateContext.getStatus());
        pedidoRepository.save(pedido);

        Movimentacao movimentacao = Movimentacao.builder()
                .data(LocalDateTime.now())
                .tipo("ESTORNO")
                .valor(pedido.getValorTotal())
                .descricao("Estorno do pedido #" + pedido.getId() + " - " + pedido.getQuantidade() + "x " + pedido.getSabor())
                .build();

        movimentacaoRepository.save(movimentacao);
    }
}
