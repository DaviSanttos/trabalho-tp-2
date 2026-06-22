import React, { useState } from 'react';
import { pedidoService } from '../services/pedidoService';
import Card from '../components/Card';
import { ShoppingCart } from 'lucide-react';

interface NovoPedidoProps {
  clienteId: number;
}

const NovoPedido: React.FC<NovoPedidoProps> = ({ clienteId }) => {
  const [sabor, setSabor] = useState<string>('FRANGO');
  const [quantidade, setQuantidade] = useState<number>(1);
  const [usarCupom, setUsarCupom] = useState(false);
  const [loading, setLoading] = useState(false);
  const [mensagem, setMensagem] = useState<{ texto: string; tipo: 'sucesso' | 'erro' } | null>(null);

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    setLoading(true);
    setMensagem(null);

    try {
      const res = await pedidoService.criar({
        clienteId,
        sabor,
        quantidade,
        usarCupom,
      });
      setMensagem({ texto: res.data.mensagem, tipo: 'sucesso' });
    } catch (error: any) {
      setMensagem({
        texto: error.response?.data?.mensagem || 'Erro ao realizar pedido',
        tipo: 'erro'
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="p-6 max-w-2xl mx-auto">
      <Card title="Novo Pedido" icon={<ShoppingCart size={24} />}>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Sabor da Coxinha</label>
            <select
              value={sabor}
              onChange={(e) => setSabor(e.target.value)}
              className="input-field"
            >
              <option value="FRANGO">Frango - R$ 5,00</option>
              <option value="CATUPIRY">Catupiry - R$ 6,00</option>
              <option value="CALABRESA">Calabresa - R$ 5,50</option>
            </select>
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-700 mb-1">Quantidade</label>
            <input
              type="number"
              value={quantidade}
              onChange={(e) => setQuantidade(Number(e.target.value))}
              className="input-field"
              min="1"
              step="1"
              required
            />
          </div>

          <div className="flex items-center space-x-2">
            <input
              type="checkbox"
              id="usarCupom"
              checked={usarCupom}
              onChange={(e) => setUsarCupom(e.target.checked)}
              className="w-4 h-4 text-orange-600 border-gray-300 rounded focus:ring-orange-500"
            />
            <label htmlFor="usarCupom" className="text-sm text-gray-700">
              Usar cupom promocional (10% de desconto)
            </label>
          </div>

          <button
            type="submit"
            disabled={loading}
            className="btn-primary w-full py-3 text-lg mt-4"
          >
            {loading ? 'Processando...' : 'Realizar Pedido'}
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

export default NovoPedido;
