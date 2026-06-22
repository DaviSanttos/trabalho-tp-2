import api from './api';
import type { Cliente, Estoque, PedidoResponse, MensagemResponse, LoginRequest, LoginResponse, PedidoRequest, Movimentacao } from '../models/types';

export const authService = {
  login: (data: LoginRequest) => api.post<LoginResponse>('/auth/login', data),
};

export const clienteService = {
  getAll: () => api.get<Cliente[]>('/clientes'),
  getById: (id: number) => api.get<Cliente>(`/clientes/${id}`),
};

export const estoqueService = {
  getEstoque: () => api.get<Estoque[]>('/estoque'),
};

export const pedidoService = {
  criar: (pedido: PedidoRequest) => api.post<MensagemResponse>('/pedidos', pedido),
  getHistorico: (clienteId?: number) => {
    const params = clienteId ? { clienteId } : {};
    return api.get<PedidoResponse[]>('/pedidos', { params });
  },
  estornar: (id: number) => api.post<MensagemResponse>(`/pedidos/${id}/estorno`),
};

export const movimentacaoService = {
  getMovimentacoes: () => api.get<Movimentacao[]>('/movimentacoes'),
};
