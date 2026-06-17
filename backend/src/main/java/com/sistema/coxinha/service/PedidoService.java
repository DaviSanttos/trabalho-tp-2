package com.sistema.coxinha.service;

import com.sistema.coxinha.command.EstornarPedidoCommand;
import com.sistema.coxinha.command.RealizarPedidoCommand;
import com.sistema.coxinha.factory.CoxinhaFactory;
import com.sistema.coxinha.model.Movimentacao;
import com.sistema.coxinha.repository.MovimentacaoRepository;
import com.sistema.coxinha.strategy.CalculoPrecoStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final ClienteService clienteService;
    private final EstoqueService estoqueService;
    private final MovimentacaoRepository movimentacaoRepository;
    private final CoxinhaFactory coxinhaFactory;
    private final CalculoPrecoStrategy precoStrategy;

    public List<Movimentacao> listarHistorico() {
        return movimentacaoRepository.findAll();
    }

    @Transactional
    public void realizarPedido(Long clienteId, String sabor, Double valorNota) {
        RealizarPedidoCommand.builder()
                .clienteId(clienteId)
                .sabor(sabor)
                .valorNota(valorNota)
                .clienteService(clienteService)
                .estoqueService(estoqueService)
                .movimentacaoRepository(movimentacaoRepository)
                .coxinhaFactory(coxinhaFactory)
                .precoStrategy(precoStrategy)
                .build()
                .executar();
    }

    @Transactional
    public void estornarPedido(Long movimentacaoId) {
        EstornarPedidoCommand.builder()
                .movimentacaoId(movimentacaoId)
                .clienteService(clienteService)
                .estoqueService(estoqueService)
                .movimentacaoRepository(movimentacaoRepository)
                .build()
                .executar();
    }
}
