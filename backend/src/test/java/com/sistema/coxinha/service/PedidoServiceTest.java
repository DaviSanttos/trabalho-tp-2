package com.sistema.coxinha.service;

import com.sistema.coxinha.factory.Coxinha;
import com.sistema.coxinha.factory.CoxinhaFactory;
import com.sistema.coxinha.model.Movimentacao;
import com.sistema.coxinha.repository.MovimentacaoRepository;
import com.sistema.coxinha.strategy.CalculoPrecoStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private ClienteService clienteService;
    @Mock
    private EstoqueService estoqueService;
    @Mock
    private MovimentacaoRepository movimentacaoRepository;
    @Mock
    private CoxinhaFactory coxinhaFactory;
    @Mock
    private CalculoPrecoStrategy precoStrategy;

    @InjectMocks
    private PedidoService pedidoService;

    @Test
    void shouldRealizarPedidoComSucesso() {
        // Arrange
        Coxinha mockCoxinha = mock(Coxinha.class);
        when(mockCoxinha.getPrecoBase()).thenReturn(5.0);
        when(coxinhaFactory.criarCoxinha(anyString())).thenReturn(mockCoxinha);
        when(precoStrategy.calcular(anyDouble())).thenReturn(5.0);

        // Act
        pedidoService.realizarPedido(1L, "FRANGO", 10.0);

        // Assert
        verify(estoqueService).baixarEstoque("FRANGO", 1);
        verify(clienteService).debitarSaldo(1L, 5.0);
        verify(movimentacaoRepository).save(any(Movimentacao.class));
    }
}
