import React, { useEffect, useState } from 'react';
import { notificacaoService } from '../services/pedidoService';
import { Bell } from 'lucide-react';

interface NavbarProps {
  onNavigate: (page: string) => void;
  currentPage: string;
  clienteNome?: string;
  clienteTipo?: string;
  onLogout: () => void;
}

const Navbar: React.FC<NavbarProps> = ({ onNavigate, currentPage, clienteNome, clienteTipo, onLogout }) => {
  const [notifCount, setNotifCount] = useState(0);

  useEffect(() => {
    if (clienteTipo === 'ADMIN') {
      notificacaoService.contagem()
        .then(res => setNotifCount(res.data))
        .catch(() => {});
    }
  }, [currentPage, clienteTipo]);

  const navItems = [
    { id: 'dashboard', label: 'Dashboard' },
    { id: 'novo-pedido', label: 'Novo Pedido' },
    { id: 'historico', label: 'Histórico' },
    { id: 'estoque', label: 'Estoque' },
    { id: 'perfil', label: 'Perfil' },
  ];

  return (
    <nav className="bg-orange-600 text-white shadow-lg">
      <div className="container mx-auto px-4">
        <div className="flex items-center justify-between h-16">
          <div className="flex items-center">
            <span className="text-xl font-bold">Coxinha System</span>
          </div>
          <div className="flex items-center space-x-4">
            {navItems.map((item) => (
              <button
                key={item.id}
                onClick={() => onNavigate(item.id)}
                className={`px-3 py-2 rounded-md text-sm font-medium transition-colors ${
                  currentPage === item.id
                    ? 'bg-orange-800 text-white'
                    : 'text-orange-100 hover:bg-orange-500 hover:text-white'
                }`}
              >
                {item.label}
              </button>
            ))}
            {clienteTipo === 'ADMIN' && (
              <button
                onClick={() => onNavigate('notificacoes')}
                className={`relative px-3 py-2 rounded-md text-sm font-medium transition-colors ${
                  currentPage === 'notificacoes'
                    ? 'bg-orange-800 text-white'
                    : 'text-orange-100 hover:bg-orange-500 hover:text-white'
                }`}
              >
                <Bell size={20} />
                {notifCount > 0 && (
                  <span className="absolute -top-1 -right-1 bg-red-500 text-white text-xs rounded-full w-5 h-5 flex items-center justify-center">
                    {notifCount}
                  </span>
                )}
              </button>
            )}
            {clienteNome && (
              <div className="flex items-center space-x-2 ml-4 pl-4 border-l border-orange-500">
                <span className="text-sm text-orange-200">{clienteNome}</span>
                <button
                  onClick={onLogout}
                  className="text-sm text-orange-200 hover:text-white"
                >
                  Sair
                </button>
              </div>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
