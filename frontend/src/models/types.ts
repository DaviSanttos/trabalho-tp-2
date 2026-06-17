export interface Cliente {
  id: number;
  nome: string;
  saldo: number;
}

export interface Movimentacao {
  id: number;
  data: string;
  valorNota: number;
  valorPedido: number;
  sabor: string;
  status: string;
}

export interface Estoque {
  id: number;
  produto: string;
  quantidade: number;
}

export interface PedidoRequest {
  clienteId: number;
  sabor: string;
  valorNota: number;
}

export interface PedidoResponse {
  sucesso: boolean;
  mensagem: string;
}
