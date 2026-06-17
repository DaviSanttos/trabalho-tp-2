package com.sistema.coxinha.command;

import com.sistema.coxinha.factory.Coxinha;
import com.sistema.coxinha.factory.CoxinhaFactory;
import com.sistema.coxinha.model.Movimentacao;
import com.sistema.coxinha.repository.MovimentacaoRepository;
import com.sistema.coxinha.service.ClienteService;
import com.sistema.coxinha.service.EstoqueService;
import com.sistema.coxinha.state.MovimentacaoContext;
import com.sistema.coxinha.strategy.CalculoPrecoStrategy;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class RealizarPedidoCommand implements Command {

    private final Long clienteId;
    private final String sabor;
    private final Double valorNota;
    
    private final ClienteService clienteService;
    private final EstoqueService estoqueService;
    private final MovimentacaoRepository movimentacaoRepository;
    private final CoxinhaFactory coxinhaFactory;
    private final CalculoPrecoStrategy precoStrategy;

    @Override
    public void执行() {
        // Use a generic name for the override if needed, but keeping the logic
    }

    @Override
    public void executar() {
        // 1. Criar coxinha via Factory
        Coxinha coxinha = coxinhaFactory.criarCoxinha(sabor);
        
        // 2. Calcular preço via Strategy
        Double precoFinal = precoStrategy.calcular(coxinha.getPrecoBase());
        
        // 3. Validar estoque
        estoqueService.baixarEstoque(sabor, 1);
        
        // 4. Debitar saldo do cliente
        clienteService.debitarSaldo(clienteId, precoFinal);
        
        // 5. Registrar movimentação
        MovimentacaoContext stateContext = new MovimentacaoContext(); // Inicia PENDENTE
        stateContext.confirmar(); // Muda para CONFIRMADA
        
        // Usando construtor manual para evitar erro de NoSuchMethodError com Lombok Builder
        Movimentacao movimentacao = new Movimentacao(
                LocalDateTime.now(),
                valorNota,
                precoFinal,
                sabor,
                stateContext.getStatus(),
                clienteId
        );
        
        movimentacaoRepository.save(movimentacao);
    }
}
