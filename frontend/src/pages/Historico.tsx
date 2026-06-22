import React, { useEffect, useState } from 'react';
import { pedidoService } from '../services/pedidoService';
import type { PedidoResponse } from '../models/types';
import Card from '../components/Card';
import { History, RotateCcw } from 'lucide-react';

interface HistoricoProps {
  clienteId: number;
}

const Historico: React.FC<HistoricoProps> = ({ clienteId }) => {
  const [historico, setHistorico] = useState<PedidoResponse[]>([]);
  const [loading, setLoading] = useState(true);

  const carregarHistorico = async () => {
    try {
      const res = await pedidoService.getHistorico(clienteId);
      setHistorico(res.data);
    } catch (error) {
      console.error('Erro ao buscar histórico:', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    carregarHistorico();
  }, [clienteId]);

  const handleEstorno = async (id: number) => {
    if (!confirm('Tem certeza que deseja estornar este pedido?')) return;

    try {
      await pedidoService.estornar(id);
      alert('Pedido estornado com sucesso!');
      carregarHistorico();
    } catch (error: any) {
      alert(error.response?.data?.mensagem || 'Erro ao estornar pedido');
    }
  };

  if (loading) return <div className="p-8 text-center">Carregando histórico...</div>;

  return (
    <div className="p-6">
      <Card title="Meus Pedidos" icon={<History size={24} />}>
        <div className="overflow-x-auto">
          <table className="table-auto">
            <thead>
              <tr>
                <th className="table-header">ID</th>
                <th className="table-header">Data</th>
                <th className="table-header">Sabor</th>
                <th className="table-header">Quantidade</th>
                <th className="table-header">Valor Total</th>
                <th className="table-header">Status</th>
                <th className="table-header text-center">Ações</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-200">
              {historico.map((p) => (
                <tr key={p.id}>
                  <td className="table-cell font-mono">#{p.id}</td>
                  <td className="table-cell">{new Date(p.data).toLocaleString()}</td>
                  <td className="table-cell">{p.sabor}</td>
                  <td className="table-cell">{p.quantidade}</td>
                  <td className="table-cell">R$ {p.valorTotal.toFixed(2)}</td>
                  <td className="table-cell">
                    <span className={`px-2 py-1 rounded-full text-xs font-semibold ${
                      p.status === 'CONFIRMADA' ? 'bg-green-100 text-green-800' : 
                      p.status === 'ESTORNADA' ? 'bg-yellow-100 text-yellow-800' : 
                      'bg-gray-100 text-gray-800'
                    }`}>
                      {p.status}
                    </span>
                  </td>
                  <td className="table-cell text-center">
                    {p.status === 'CONFIRMADA' && (
                      <button
                        onClick={() => handleEstorno(p.id)}
                        className="text-orange-600 hover:text-orange-800 p-1 flex items-center justify-center mx-auto"
                        title="Estornar Pedido"
                      >
                        <RotateCcw size={18} className="mr-1" />
                        <span className="text-xs font-medium">Estornar</span>
                      </button>
                    )}
                  </td>
                </tr>
              ))}
              {historico.length === 0 && (
                <tr>
                  <td colSpan={7} className="table-cell text-center text-gray-500">Nenhum pedido encontrado.</td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </Card>
    </div>
  );
};

export default Historico;
