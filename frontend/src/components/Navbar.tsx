import React from 'react';

interface NavbarProps {
  onNavigate: (page: string) => void;
  currentPage: string;
  clienteNome?: string;
  onLogout: () => void;
}

const Navbar: React.FC<NavbarProps> = ({ onNavigate, currentPage, clienteNome, onLogout }) => {
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
