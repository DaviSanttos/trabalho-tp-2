import { useState } from 'react';
import Navbar from './components/Navbar';
import Dashboard from './pages/Dashboard';
import NovaTroca from './pages/NovaTroca';
import Historico from './pages/Historico';
import EstoquePage from './pages/Estoque';

function App() {
  const [currentPage, setCurrentPage] = useState<'dashboard' | 'nova-troca' | 'historico' | 'estoque'>('dashboard');

  const renderPage = () => {
    switch (currentPage) {
      case 'dashboard':
        return <Dashboard />;
      case 'nova-troca':
        return <NovaTroca />;
      case 'historico':
        return <Historico />;
      case 'estoque':
        return <EstoquePage />;
      default:
        return <Dashboard />;
    }
  };

  return (
    <div className="min-h-screen bg-gray-100">
      <Navbar onNavigate={setCurrentPage} currentPage={currentPage} />
      <main className="container mx-auto py-8">
        {renderPage()}
      </main>
    </div>
  );
}

export default App;
