package com.sistema.coxinha.service;

import com.sistema.coxinha.command.EstornarPedidoCommand;
import com.sistema.coxinha.command.RealizarPedidoCommand;
import com.sistema.coxinha.factory.CoxinhaCalabresaFactory;
import com.sistema.coxinha.factory.CoxinhaCatupiryFactory;
import com.sistema.coxinha.factory.CoxinhaFactory;
import com.sistema.coxinha.factory.CoxinhaFrangoFactory;
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
    private final CoxinhaFrangoFactory coxinhaFrangoFactory;
    private final CoxinhaCatupiryFactory coxinhaCatupiryFactory;
    private final CoxinhaCalabresaFactory coxinhaCalabresaFactory;
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

        CoxinhaFactory factory = selecionarFactory(sabor);

        RealizarPedidoCommand.builder()
                .clienteId(clienteId)
                .quantidade(quantidade)
                .estoqueService(estoqueService)
                .pedidoRepository(pedidoRepository)
                .movimentacaoRepository(movimentacaoRepository)
                .coxinhaFactory(factory)
                .precoStrategy(strategy)
                .build()
                .executar();
    }

    private CoxinhaFactory selecionarFactory(String sabor) {
        if (sabor == null) {
            throw new IllegalArgumentException("Sabor nao pode ser nulo");
        }
        return switch (sabor.toUpperCase()) {
            case "FRANGO" -> coxinhaFrangoFactory;
            case "CATUPIRY" -> coxinhaCatupiryFactory;
            case "CALABRESA" -> coxinhaCalabresaFactory;
            default -> throw new IllegalArgumentException("Sabor desconhecido: " + sabor);
        };
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
