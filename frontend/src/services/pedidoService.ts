import api from './api';
import type { Cliente, Estoque, PedidoResponse, MensagemResponse, LoginRequest, LoginResponse, PedidoRequest, Movimentacao, Notificacao } from '../models/types';

export const authService = {
  login: (data: LoginRequest) => api.post<LoginResponse>('/auth/login', data),
};

export const clienteService = {
  getAll: () => api.get<Cliente[]>('/clientes'),
  getById: (id: number) => api.get<Cliente>(`/clientes/${id}`),
};

export const estoqueService = {
  getEstoque: () => api.get<Estoque[]>('/estoque'),
  repor: (sabor: string, quantidade: number) => api.post<MensagemResponse>('/estoque/repor', { sabor, quantidade }),
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

export const notificacaoService = {
  listar: () => api.get<Notificacao[]>('/notificacoes'),
  listarNaoLidas: () => api.get<Notificacao[]>('/notificacoes/nao-lidas'),
  contagem: () => api.get<number>('/notificacoes/contagem'),
  marcarLida: (id: number) => api.post(`/notificacoes/${id}/marcar-lida`),
  marcarTodasLidas: () => api.post('/notificacoes/marcar-todas-lidas'),
};
