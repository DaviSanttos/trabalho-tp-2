import React from 'react';

interface NavbarProps {
  onNavigate: (page: 'dashboard' | 'nova-troca' | 'historico' | 'estoque') => void;
  currentPage: string;
}

const Navbar: React.FC<NavbarProps> = ({ onNavigate, currentPage }) => {
  const navItems = [
    { id: 'dashboard', label: 'Dashboard' },
    { id: 'nova-troca', label: 'Nova Troca' },
    { id: 'historico', label: 'Histórico' },
    { id: 'estoque', label: 'Estoque' },
  ];

  return (
    <nav className="bg-orange-600 text-white shadow-lg">
      <div className="container mx-auto px-4">
        <div className="flex items-center justify-between h-16">
          <div className="flex items-center">
            <span className="text-xl font-bold">Coxinha System 🍗</span>
          </div>
          <div className="flex space-x-4">
            {navItems.map((item) => (
              <button
                key={item.id}
                onClick={() => onNavigate(item.id as any)}
                className={`px-3 py-2 rounded-md text-sm font-medium transition-colors ${
                  currentPage === item.id
                    ? 'bg-orange-800 text-white'
                    : 'text-orange-100 hover:bg-orange-500 hover:text-white'
                }`}
              >
                {item.label}
              </button>
            ))}
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
