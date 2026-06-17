import React, { useEffect, useState } from 'react';
import { clienteService, pedidoService } from '../services/pedidoService';
import type { Cliente } from '../models/types';
import Card from '../components/Card';
import { ShoppingCart } from 'lucide-react';

const NovaTroca: React.FC = () => {
  const [clientes, setClientes] = useState<Cliente[]>([]);
  const [clienteId, setClienteId] = useState<string>('');
  const [sabor, setSabor] = useState<string>('FRANGO');
  const [valorNota, setValorNota] = useState<number>(10);
  const [loading, setLoading] = useState(false);
  const [mensagem, setMensagem] = useState<{ texto: string; tipo: 'sucesso' | 'erro' } | null>(null);

  useEffect(() => {
    clienteService.getAll().then((res) => setClientes(res.data));
  }, []);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!clienteId) return;

    setLoading(true);
    setMensagem(null);

    try {
      const res = await pedidoService.criar({
        clienteId: Number(clienteId),
        sabor,
        valorNota,
      });
      setMensagem({ texto: res.data.mensagem, tipo: 'sucesso' });
      // Limpar campos se necessário ou recarregar saldo
    } catch (error: any) {
      setMensagem({ 
        texto: error.response?.data?.message || 'Erro ao realizar pedido', 
        tipo: 'erro' 
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="p-6 max-w-2xl mx-auto">
      <Card title="Nova Troca de Nota por Coxinha" icon={<ShoppingCart size={24} />}>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Cliente</label>
            <select
              value={clienteId}
              onChange={(e) => setClienteId(e.target.value)}
              className="input-field"
              required
            >
              <option value="">Selecione um cliente</option>
              {clientes.map((c) => (
                <option key={c.id} value={c.id}>
                  {c.nome} (Saldo: R$ {c.saldo.toFixed(2)})
                </option>
              ))}
            </select>
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Sabor da Coxinha</label>
            <select
              value={sabor}
              onChange={(e) => setSabor(e.target.value)}
              className="input-field"
            >
              <option value="FRANGO">Frango</option>
              <option value="CATUPIRY">Catupiry</option>
              <option value="CALABRESA">Calabresa</option>
            </select>
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Valor da Nota (R$)</label>
            <input
              type="number"
              value={valorNota}
              onChange={(e) => setValorNota(Number(e.target.value))}
              className="input-field"
              min="1"
              step="0.01"
              required
            />
          </div>

          <button
            type="submit"
            disabled={loading || !clienteId}
            className="btn-primary w-full py-3 text-lg mt-4"
          >
            {loading ? 'Processando...' : 'Trocar Nota por Coxinha'}
          </button>
        </form>

        {mensagem && (
          <div className={`mt-6 p-4 rounded-md ${
            mensagem.tipo === 'sucesso' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'
          }`}>
            {mensagem.texto}
          </div>
        )}
      </Card>
    </div>
  );
};

export default NovaTroca;
