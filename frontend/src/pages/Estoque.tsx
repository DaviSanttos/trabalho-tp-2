import React, { useEffect, useState } from 'react';
import { estoqueService } from '../services/pedidoService';
import type { Estoque } from '../models/types';
import Card from '../components/Card';
import { Package, AlertTriangle, Plus } from 'lucide-react';

interface EstoquePageProps {
  admin?: boolean;
}

const EstoquePage: React.FC<EstoquePageProps> = ({ admin = false }) => {
  const [estoque, setEstoque] = useState<Estoque[]>([]);
  const [loading, setLoading] = useState(true);

  const fetchEstoque = () => {
    setLoading(true);
    estoqueService.getEstoque()
      .then((res) => setEstoque(res.data))
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    fetchEstoque();
  }, []);

  const handleRepor = async (sabor: string) => {
    try {
      await estoqueService.repor(sabor, 5);
      fetchEstoque();
    } catch (error) {
      console.error('Erro ao repor estoque:', error);
    }
  };

  if (loading) return <div className="p-8 text-center">Carregando estoque...</div>;

  return (
    <div className="p-6">
      <Card title="Controle de Estoque" icon={<Package size={24} />}>
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mt-4">
          {estoque.map((item) => (
            <div 
              key={item.id} 
              className={`p-6 rounded-lg border-2 flex flex-col items-center justify-center ${
                item.quantidade < 5 ? 'border-red-200 bg-red-50' : 'border-green-200 bg-green-50'
              }`}
            >
              <h3 className="text-lg font-bold text-gray-800 mb-2">{item.produto}</h3>
              <div className="text-4xl font-extrabold text-gray-900 mb-2">
                {item.quantidade}
              </div>
              <p className="text-sm text-gray-500">unidades disponíveis</p>
              
              {item.quantidade < 5 && (
                <div className="mt-4 flex items-center text-red-700 font-medium">
                  <AlertTriangle size={18} className="mr-1" />
                  <span>Estoque Baixo!</span>
                </div>
              )}

              {admin && (
                <button
                  onClick={() => handleRepor(item.produto)}
                  className="mt-4 flex items-center px-4 py-2 bg-orange-600 text-white rounded-md hover:bg-orange-700 text-sm"
                >
                  <Plus size={16} className="mr-1" />
                  Repor (+5)
                </button>
              )}
            </div>
          ))}
        </div>
      </Card>
    </div>
  );
};

export default EstoquePage;
