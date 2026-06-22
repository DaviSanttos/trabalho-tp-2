import React, { useEffect, useState } from 'react';
import { clienteService } from '../services/pedidoService';
import type { Cliente } from '../models/types';
import Card from '../components/Card';
import { User } from 'lucide-react';

interface PerfilProps {
  clienteId: number;
}

const Perfil: React.FC<PerfilProps> = ({ clienteId }) => {
  const [cliente, setCliente] = useState<Cliente | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    clienteService.getById(clienteId)
      .then((res) => setCliente(res.data))
      .finally(() => setLoading(false));
  }, [clienteId]);

  if (loading) return <div className="p-8 text-center">Carregando perfil...</div>;
  if (!cliente) return <div className="p-8 text-center">Cliente não encontrado</div>;

  return (
    <div className="p-6 max-w-lg mx-auto">
      <Card title="Meu Perfil" icon={<User size={24} />}>
        <div className="space-y-4">
          <div className="border-b pb-4">
            <label className="text-sm text-gray-500">Nome</label>
            <p className="text-lg font-medium text-gray-800">{cliente.nome}</p>
          </div>
          <div className="border-b pb-4">
            <label className="text-sm text-gray-500">Email</label>
            <p className="text-lg font-medium text-gray-800">{cliente.email}</p>
          </div>
          <div>
            <label className="text-sm text-gray-500">ID</label>
            <p className="text-lg font-medium text-gray-800">#{cliente.id}</p>
          </div>
        </div>
      </Card>
    </div>
  );
};

export default Perfil;
