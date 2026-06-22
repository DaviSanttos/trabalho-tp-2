package com.sistema.coxinha.service;

import com.sistema.coxinha.command.EstornarPedidoCommand;
import com.sistema.coxinha.command.RealizarPedidoCommand;
import com.sistema.coxinha.factory.CoxinhaFactory;
import com.sistema.coxinha.model.Pedido;
import com.sistema.coxinha.repository.MovimentacaoRepository;
import com.sistema.coxinha.repository.PedidoRepository;
import com.sistema.coxinha.strategy.CalculoPrecoStrategy;
import com.sistema.coxinha.strategy.PrecoPadrao;
import com.sistema.coxinha.strategy.PrecoPromocional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final EstoqueService estoqueService;
    private final PedidoRepository pedidoRepository;
    private final MovimentacaoRepository movimentacaoRepository;
    private final CoxinhaFactory coxinhaFactory;
    private final PrecoPadrao precoPadrao;
    private final PrecoPromocional precoPromocional;

    public List<Pedido> listarHistorico() {
        return pedidoRepository.findAll();
    }

    public List<Pedido> listarPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    @Transactional
    public void realizarPedido(Long clienteId, String sabor, Integer quantidade, boolean usarCupom) {
        CalculoPrecoStrategy strategy = usarCupom ? precoPromocional : precoPadrao;

        RealizarPedidoCommand.builder()
                .clienteId(clienteId)
                .sabor(sabor)
                .quantidade(quantidade)
                .estoqueService(estoqueService)
                .pedidoRepository(pedidoRepository)
                .movimentacaoRepository(movimentacaoRepository)
                .coxinhaFactory(coxinhaFactory)
                .precoStrategy(strategy)
                .build()
                .executar();
    }

    @Transactional
    public void estornarPedido(Long pedidoId) {
        EstornarPedidoCommand.builder()
                .pedidoId(pedidoId)
                .estoqueService(estoqueService)
                .pedidoRepository(pedidoRepository)
                .movimentacaoRepository(movimentacaoRepository)
                .build()
                .executar();
    }
}
