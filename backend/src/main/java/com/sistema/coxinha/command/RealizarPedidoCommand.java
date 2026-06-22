package com.sistema.coxinha.command;

import com.sistema.coxinha.factory.Coxinha;
import com.sistema.coxinha.factory.CoxinhaFactory;
import com.sistema.coxinha.model.Movimentacao;
import com.sistema.coxinha.model.Pedido;
import com.sistema.coxinha.repository.MovimentacaoRepository;
import com.sistema.coxinha.repository.PedidoRepository;
import com.sistema.coxinha.service.EstoqueService;
import com.sistema.coxinha.state.PedidoContext;
import com.sistema.coxinha.strategy.CalculoPrecoStrategy;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class RealizarPedidoCommand implements Command {

    private final Long clienteId;
    private final Integer quantidade;

    private final EstoqueService estoqueService;
    private final PedidoRepository pedidoRepository;
    private final MovimentacaoRepository movimentacaoRepository;
    private final CoxinhaFactory coxinhaFactory;
    private final CalculoPrecoStrategy precoStrategy;

    @Override
    public void executar() {
        Coxinha coxinha = coxinhaFactory.criarCoxinha();

        String sabor = coxinha.getSabor();
        Double precoFinal = precoStrategy.calcular(coxinha.getPrecoBase()) * quantidade;

        estoqueService.baixarEstoque(sabor, quantidade);

        PedidoContext stateContext = new PedidoContext();
        stateContext.confirmar();

        Pedido pedido = Pedido.builder()
                .clienteId(clienteId)
                .data(LocalDateTime.now())
                .sabor(sabor)
                .quantidade(quantidade)
                .valorTotal(precoFinal)
                .status(stateContext.getStatus())
                .build();

        pedidoRepository.save(pedido);

        Movimentacao movimentacao = Movimentacao.builder()
                .data(LocalDateTime.now())
                .tipo("PEDIDO")
                .valor(precoFinal)
                .descricao("Pedido #" + pedido.getId() + " - " + quantidade + "x " + sabor)
                .build();

        movimentacaoRepository.save(movimentacao);
    }
}
