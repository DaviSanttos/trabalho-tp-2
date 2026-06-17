import React, { useEffect, useState } from 'react';
import { clienteService, estoqueService, pedidoService } from '../services/pedidoService';
import type { Cliente, Estoque, Movimentacao } from '../models/types';
import Card from '../components/Card';
import { Users, Package, History, TrendingUp } from 'lucide-react';

const Dashboard: React.FC = () => {
  const [clientes, setClientes] = useState<Cliente[]>([]);
  const [estoque, setEstoque] = useState<Estoque[]>([]);
  const [historico, setHistorico] = useState<Movimentacao[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [resClientes, resEstoque, resHistorico] = await Promise.all([
          clienteService.getAll(),
          estoqueService.getEstoque(),
          pedidoService.getHistorico(),
        ]);
        setClientes(resClientes.data);
        setEstoque(resEstoque.data);
        setHistorico(resHistorico.data.slice(0, 5)); // Top 5 recentes
      } catch (error) {
        console.error('Erro ao buscar dados do dashboard:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  if (loading) return <div className="p-8 text-center">Carregando dados...</div>;

  return (
    <div className="p-6">
      <h1 className="text-3xl font-bold text-gray-800 mb-8">Dashboard do Sistema</h1>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-8">
        <Card title="Clientes e Saldos" icon={<Users size={24} />}>
          <ul className="divide-y divide-gray-100">
            {clientes.map((c) => (
              <li key={c.id} className="py-2 flex justify-between">
                <span className="text-gray-700">{c.nome}</span>
                <span className="font-bold text-orange-600">R$ {c.saldo.toFixed(2)}</span>
              </li>
            ))}
          </ul>
        </Card>

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
            <p className="text-4xl font-bold text-orange-600">{historico.length}</p>
          </div>
        </Card>
      </div>

      <Card title="Últimas Movimentações" icon={<History size={24} />}>
        <div className="overflow-x-auto">
          <table className="table-auto">
            <thead>
              <tr>
                <th className="table-header">Data</th>
                <th className="table-header">Sabor</th>
                <th className="table-header">Valor</th>
                <th className="table-header">Status</th>
              </tr>
            </thead>
            <tbody className="divide-y divide-gray-200">
              {historico.map((m) => (
                <tr key={m.id}>
                  <td className="table-cell">{new Date(m.data).toLocaleString()}</td>
                  <td className="table-cell">{m.sabor}</td>
                  <td className="table-cell">R$ {m.valorPedido.toFixed(2)}</td>
                  <td className="table-cell">
                    <span className={`px-2 py-1 rounded-full text-xs font-semibold ${
                      m.status === 'CONFIRMADA' ? 'bg-green-100 text-green-800' : 
                      m.status === 'ESTORNADA' ? 'bg-yellow-100 text-yellow-800' : 
                      'bg-gray-100 text-gray-800'
                    }`}>
                      {m.status}
                    </span>
                  </td>
                </tr>
              ))}
              {historico.length === 0 && (
                <tr>
                  <td colSpan={4} className="table-cell text-center text-gray-500">Nenhuma movimentação registrada.</td>
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
