import { useState } from 'react';
import Navbar from './components/Navbar';
import Login from './pages/Login';
import Dashboard from './pages/Dashboard';
import NovoPedido from './pages/NovoPedido';
import Historico from './pages/Historico';
import EstoquePage from './pages/Estoque';
import Perfil from './pages/Perfil';

function App() {
  const [autenticado, setAutenticado] = useState(false);
  const [clienteId, setClienteId] = useState(0);
  const [clienteNome, setClienteNome] = useState('');
  const [currentPage, setCurrentPage] = useState('dashboard');

  const handleLogin = (id: number, nome: string) => {
    setAutenticado(true);
    setClienteId(id);
    setClienteNome(nome);
    setCurrentPage('dashboard');
  };

  const handleLogout = () => {
    setAutenticado(false);
    setClienteId(0);
    setClienteNome('');
    setCurrentPage('dashboard');
  };

  if (!autenticado) {
    return <Login onLogin={handleLogin} />;
  }

  const renderPage = () => {
    switch (currentPage) {
      case 'dashboard':
        return <Dashboard clienteId={clienteId} />;
      case 'novo-pedido':
        return <NovoPedido clienteId={clienteId} />;
      case 'historico':
        return <Historico clienteId={clienteId} />;
      case 'estoque':
        return <EstoquePage />;
      case 'perfil':
        return <Perfil clienteId={clienteId} />;
      default:
        return <Dashboard clienteId={clienteId} />;
    }
  };

  return (
    <div className="min-h-screen bg-gray-100">
      <Navbar
        onNavigate={setCurrentPage}
        currentPage={currentPage}
        clienteNome={clienteNome}
        onLogout={handleLogout}
      />
      <main className="container mx-auto py-8">
        {renderPage()}
      </main>
    </div>
  );
}

export default App;
