import React, { useEffect, useState } from 'react';
import { notificacaoService } from '../services/pedidoService';
import type { Notificacao } from '../models/types';
import Card from '../components/Card';
import { Bell, AlertTriangle, Package, CheckCheck } from 'lucide-react';

const Notificacoes: React.FC = () => {
  const [notificacoes, setNotificacoes] = useState<Notificacao[]>([]);
  const [loading, setLoading] = useState(true);

  const fetchNotificacoes = () => {
    setLoading(true);
    notificacaoService.listar()
      .then(res => setNotificacoes(res.data))
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    fetchNotificacoes();
  }, []);

  const handleMarcarLida = async (id: number) => {
    await notificacaoService.marcarLida(id);
    fetchNotificacoes();
  };

  const handleMarcarTodasLidas = async () => {
    await notificacaoService.marcarTodasLidas();
    fetchNotificacoes();
  };

  const getIcon = (tipo: string) => {
    switch (tipo) {
      case 'ESTOQUE_BAIXO': return <AlertTriangle size={20} className="text-red-500" />;
      case 'ESTOQUE_ALTERADO': return <Package size={20} className="text-blue-500" />;
      default: return <Bell size={20} className="text-gray-500" />;
    }
  };

  if (loading) return <div className="p-8 text-center">Carregando notificações...</div>;

  return (
    <div className="p-6 max-w-4xl mx-auto">
      <Card title="Notificações" icon={<Bell size={24} />}>
        {notificacoes.length > 0 && (
          <div className="flex justify-end mb-4">
            <button
              onClick={handleMarcarTodasLidas}
              className="flex items-center text-sm text-orange-600 hover:text-orange-800"
            >
              <CheckCheck size={16} className="mr-1" />
              Marcar todas como lidas
            </button>
          </div>
        )}
        <div className="space-y-3">
          {notificacoes.length === 0 && (
            <p className="text-gray-500 text-center py-8">Nenhuma notificação</p>
          )}
          {notificacoes.map((n) => (
            <div
              key={n.id}
              className={`flex items-start p-4 rounded-lg border ${
                n.lida ? 'bg-white border-gray-200' : 'bg-orange-50 border-orange-200'
              }`}
            >
              <div className="mr-3 mt-1">{getIcon(n.tipo)}</div>
              <div className="flex-1">
                <p className={`text-sm ${n.lida ? 'text-gray-600' : 'text-gray-900 font-medium'}`}>
                  {n.mensagem}
                </p>
                <p className="text-xs text-gray-400 mt-1">
                  {new Date(n.data).toLocaleString()}
                </p>
              </div>
              {!n.lida && (
                <button
                  onClick={() => handleMarcarLida(n.id)}
                  className="text-xs text-orange-600 hover:text-orange-800 ml-2"
                >
                  Marcar lida
                </button>
              )}
            </div>
          ))}
        </div>
      </Card>
    </div>
  );
};

export default Notificacoes;
