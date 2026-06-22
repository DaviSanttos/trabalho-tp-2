package com.sistema.coxinha.service;

import com.sistema.coxinha.factory.Coxinha;
import com.sistema.coxinha.factory.CoxinhaFactory;
import com.sistema.coxinha.model.Movimentacao;
import com.sistema.coxinha.model.Pedido;
import com.sistema.coxinha.repository.MovimentacaoRepository;
import com.sistema.coxinha.repository.PedidoRepository;
import com.sistema.coxinha.strategy.PrecoPadrao;
import com.sistema.coxinha.strategy.PrecoPromocional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private EstoqueService estoqueService;
    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private MovimentacaoRepository movimentacaoRepository;
    @Mock
    private CoxinhaFactory coxinhaFactory;
    @Mock
    private PrecoPadrao precoPadrao;
    @Mock
    private PrecoPromocional precoPromocional;

    @InjectMocks
    private PedidoService pedidoService;

    @Test
    void shouldRealizarPedidoComSucesso() {
        Coxinha mockCoxinha = mock(Coxinha.class);
        when(mockCoxinha.getPrecoBase()).thenReturn(5.0);
        when(coxinhaFactory.criarCoxinha(anyString())).thenReturn(mockCoxinha);
        when(precoPadrao.calcular(anyDouble())).thenReturn(5.0);

        pedidoService.realizarPedido(1L, "FRANGO", 2, false);

        verify(estoqueService).baixarEstoque("FRANGO", 2);
        verify(pedidoRepository).save(any(Pedido.class));
        verify(movimentacaoRepository).save(any(Movimentacao.class));
    }

    @Test
    void shouldRealizarPedidoComCupom() {
        Coxinha mockCoxinha = mock(Coxinha.class);
        when(mockCoxinha.getPrecoBase()).thenReturn(5.0);
        when(coxinhaFactory.criarCoxinha(anyString())).thenReturn(mockCoxinha);
        when(precoPromocional.calcular(anyDouble())).thenReturn(4.5);

        pedidoService.realizarPedido(1L, "FRANGO", 2, true);

        verify(estoqueService).baixarEstoque("FRANGO", 2);
        verify(pedidoRepository).save(any(Pedido.class));
        verify(movimentacaoRepository).save(any(Movimentacao.class));
    }
}
