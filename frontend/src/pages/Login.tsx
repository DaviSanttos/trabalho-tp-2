import React, { useState } from 'react';
import { authService } from '../services/pedidoService';
import Card from '../components/Card';
import { LogIn } from 'lucide-react';

interface LoginProps {
  onLogin: (clienteId: number, nome: string) => void;
}

const Login: React.FC<LoginProps> = ({ onLogin }) => {
  const [email, setEmail] = useState('');
  const [senha, setSenha] = useState('');
  const [loading, setLoading] = useState(false);
  const [erro, setErro] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setErro('');

    try {
      const res = await authService.login({ email, senha });
      if (res.data.sucesso) {
        onLogin(res.data.clienteId, res.data.nome);
      } else {
        setErro(res.data.mensagem);
      }
    } catch (error: any) {
      setErro(error.response?.data?.mensagem || 'Erro ao realizar login');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <div className="w-full max-w-md">
        <Card title="Login" icon={<LogIn size={24} />}>
          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Email</label>
              <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                className="input-field"
                placeholder="seu@email.com"
                required
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Senha</label>
              <input
                type="password"
                value={senha}
                onChange={(e) => setSenha(e.target.value)}
                className="input-field"
                placeholder="Sua senha"
                required
              />
            </div>

            <button
              type="submit"
              disabled={loading}
              className="btn-primary w-full py-3 text-lg mt-4"
            >
              {loading ? 'Entrando...' : 'Entrar'}
            </button>

            {erro && (
              <div className="p-4 rounded-md bg-red-100 text-red-800">
                {erro}
              </div>
            )}
          </form>
        </Card>
      </div>
    </div>
  );
};

export default Login;
