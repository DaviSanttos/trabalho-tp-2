import api from './api';
import type { Cliente, Estoque, Movimentacao, PedidoRequest, PedidoResponse } from '../models/types';

export const clienteService = {
  getAll: () => api.get<Cliente[]>('/clientes'),
  getById: (id: number) => api.get<Cliente>(`/clientes/${id}`),
};

export const estoqueService = {
  getEstoque: () => api.get<Estoque[]>('/estoque'),
};

export const pedidoService = {
  criar: (pedido: PedidoRequest) => api.post<PedidoResponse>('/pedidos', pedido),
  getHistorico: () => api.get<Movimentacao[]>('/pedidos'),
  estornar: (id: number) => api.post<PedidoResponse>(`/pedidos/${id}/estorno`),
};
