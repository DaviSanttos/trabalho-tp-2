# Sistema de Troca de Notas por Coxinhas 🍗

Este projeto é um simulador de troca de notas por coxinhas, desenvolvido como um trabalho prático.

## Tecnologias

### Back-End
- Java 17 / Spring Boot 3
- H2 Database (In-Memory)
- Spring Data JPA
- Maven

### Front-End
- React + TypeScript
- Vite
- Tailwind CSS
- Axios / Lucide React

## Como Executar

### 1. Back-End
- Navegue até a pasta `backend/`
- Execute: `./mvnw spring-boot:run` ou via sua IDE favorita.
- O servidor iniciará em `http://localhost:8080`

### 2. Front-End
- Navegue até a pasta `frontend/`
- Instale as dependências: `npm install`
- Execute: `npm run dev`
- O servidor iniciará em `http://localhost:3000`

## Padrões de Projeto Implementados
- **Strategy**: Cálculo de Preço.
- **Factory Method**: Criação de Coxinhas.
- **Observer**: Monitoramento de Estoque.
- **Command**: Realização e Estorno de Pedidos.
- **State**: Gerenciamento de Status de Movimentação.
- **DTO**: Transferência de dados entre camadas.

## Documentação Detalhada
Consulte a pasta `docs/` para ver o progresso de cada fase do desenvolvimento.
