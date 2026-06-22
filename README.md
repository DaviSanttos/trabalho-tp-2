# Sistema de Pedidos de Coxinhas

Sistema full stack para gerenciamento de pedidos de coxinhas com controle de
estoque, notificações e perfis de acesso (admin/cliente).

## Requisitos

- **Java 17+** e **Maven** (para o backend)
- **Node.js 18+** e **npm** (para o frontend)

## Como Rodar

### 1. Backend (Spring Boot)

```bash
cd backend
mvn spring-boot:run
```

A API inicia em `http://localhost:8080`.

### 2. Frontend (React + Vite)

```bash
cd frontend
npm install
npm run dev
```

A interface inicia em `http://localhost:3000`.

---

### Ou use o script automatizado

Execute `INICIAR_SISTEMA.bat` na raiz do projeto — ele sobe ambos os servidores.

## Credenciais

| Usuário | Email | Senha | Perfil |
|---|---|---|---|
| Davi | davi@email.com | 123456 | ADMIN |
| Maria | maria@email.com | 123456 | CLIENTE |

## Padrões de Projeto

- **Command**: Realizar e estornar pedidos
- **Strategy**: Cálculo de preço (normal / promocional)
- **Factory Method**: Criação de coxinhas por sabor
- **Observer**: Notificações de alterações no estoque
- **State**: Transições de status do pedido

## Documentação

Consulte a pasta `docs/`.
