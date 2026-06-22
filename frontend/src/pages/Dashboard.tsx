import React, { useEffect, useState } from 'react';
import { estoqueService, pedidoService } from '../services/pedidoService';
import type { Estoque, PedidoResponse } from '../models/types';
import Card from '../components/Card';
import { Package, History, TrendingUp } from 'lucide-react';

interface DashboardProps {
  clienteId: number;
}

const Dashboard: React.FC<DashboardProps> = ({ clienteId }) => {
  const [estoque, setEstoque] = useState<Estoque[]>([]);
  const [pedidos, setPedidos] = useState<PedidoResponse[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [resEstoque, resPedidos] = await Promise.all([
          estoqueService.getEstoque(),
          pedidoService.getHistorico(clienteId),
        ]);
        setEstoque(resEstoque.data);
        setPedidos(resPedidos.data.slice(0, 5));
      } catch (error) {
        console.error('Erro ao buscar dados do dashboard:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [clienteId]);

  if (loading) return <div className="p-8 text-center">Carregando dados...</div>;

  return (
    <div className="p-6">
      <h1 className="text-3xl font-bold text-gray-800 mb-8">Dashboard</h1>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-8">
        <Card title="Estoque Atual" icon={<Package size={24} />}>
          <ul className="divide-y divide-gray-100">
            {estoque.map((e) => (
              <li key={e.id} className="py-2 flex justify-between">
                <span className="text-gray-700">{e.produto}</span>
                <span className={`font-bold ${e.quantidade < 5 ? 'text-red-600' : 'text-green-600'}`}>
                  {e.quantidade} un
                </span>
              </li>
            ))}
          </ul>
        </Card>

        <Card title="Resumo de Atividade" icon={<TrendingUp size={24} />}>
          <div className="text-center py-4">
            <p className="text-gray-500">Total de pedidos realizados:</p>
            <p className="text-4xl font-bold text-orange-600">{pedidos.length}</p>
          </div>
        </Card>
      </div>

      <Card title="Últimos Pedidos" icon={<History size={24} />}>
        <div className="overflow-x-auto">
          <table className="table-auto">
            <thead>
              <tr>
                <th className="table-header">ID</th>
                <th className="table-header">Sabor</th>
                <th className="table-header">Qtd</th>
                <th className="table-header">Valor</th>
                <th className="table-header">Status</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-200">
              {pedidos.map((p) => (
                <tr key={p.id}>
                  <td className="table-cell font-mono">#{p.id}</td>
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
                </tr>
              ))}
              {pedidos.length === 0 && (
                <tr>
                  <td colSpan={5} className="table-cell text-center text-gray-500">Nenhum pedido registrado.</td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </Card>
    </div>
  );
};

export default Dashboard;
