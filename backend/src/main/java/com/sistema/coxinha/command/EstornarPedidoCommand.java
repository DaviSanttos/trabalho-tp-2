package com.sistema.coxinha.command;

import com.sistema.coxinha.model.Movimentacao;
import com.sistema.coxinha.repository.MovimentacaoRepository;
import com.sistema.coxinha.service.ClienteService;
import com.sistema.coxinha.service.EstoqueService;
import com.sistema.coxinha.state.MovimentacaoContext;
import lombok.Builder;

@Builder
public class EstornarPedidoCommand implements Command {

    private final Long movimentacaoId;
    private final ClienteService clienteService;
    private final EstoqueService estoqueService;
    private final MovimentacaoRepository movimentacaoRepository;

    @Override
    public void executar() {
        Movimentacao movimentacao = movimentacaoRepository.findById(movimentacaoId)
                .orElseThrow(() -> new RuntimeException("Movimentação não encontrada"));

        if ("ESTORNADA".equals(movimentacao.getStatus())) {
            throw new RuntimeException("Este pedido já foi estornado");
        }

        // 1. Validar e mudar estado via State Pattern
        MovimentacaoContext stateContext = new MovimentacaoContext();
        // Assume que o status atual é CONFIRMADA para permitir estorno
        stateContext.estornar();

        // 2. Devolver estoque
        estoqueService.adicionarEstoque(movimentacao.getSabor(), 1);

        // 3. Reverter saldo
        clienteService.adicionarSaldo(movimentacao.getClienteId(), movimentacao.getValorPedido());

        // 4. Atualizar status da movimentação
        movimentacao.setStatus(stateContext.getStatus());
        movimentacaoRepository.save(movimentacao);
    }
}
