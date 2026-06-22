export interface Cliente {
  id: number;
  nome: string;
  email: string;
}

export interface Movimentacao {
  id: number;
  data: string;
  tipo: string;
  valor: number;
  descricao: string;
}

export interface Estoque {
  id: number;
  produto: string;
  quantidade: number;
}

export interface PedidoResponse {
  id: number;
  clienteId: number;
  nomeCliente: string;
  data: string;
  sabor: string;
  quantidade: number;
  valorTotal: number;
  status: string;
}

export interface MensagemResponse {
  sucesso: boolean;
  mensagem: string;
}

export interface LoginRequest {
  email: string;
  senha: string;
}

export interface LoginResponse {
  clienteId: number;
  nome: string;
  email: string;
  sucesso: boolean;
  mensagem: string;
}

export interface PedidoRequest {
  clienteId: number;
  sabor: string;
  quantidade: number;
  usarCupom: boolean;
}
