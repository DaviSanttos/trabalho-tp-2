# Sistema de Pedidos de Coxinhas

## Visão Geral

Sistema completo para gerenciamento de pedidos de coxinhas, desenvolvido com Java Spring Boot (Back-End) e React + Vite + TypeScript (Front-End). A comunicação entre as camadas ocorre exclusivamente via HTTP + JSON.

---

## Arquitetura (MVC)

O sistema segue o padrão **MVC (Model-View-Controller)**:

```
back-end/
├── model/        → Entidades JPA e regras de negócio
├── view/         → Front-End Web (React)
├── controller/   → RestControllers que recebem HTTP e retornam JSON
├── service/      → Lógica de negócio e orquestração
├── repository/   → Persistência via Spring Data JPA
├── dto/          → Objetos de transferência (não expõe entidades diretamente)
├── strategy/     → Padrão Strategy (cálculo de preço)
├── factory/      → Padrão Factory Method (criação de coxinhas)
├── observer/     → Padrão Observer (monitoramento de estoque)
├── command/      → Padrão Command (ações do sistema)
├── state/        → Padrão State (estados dos pedidos)
├── config/       → Configurações e inicialização de dados
├── exception/    → Tratamento global de exceções
└── util/         → Utilitários
```

```
front-end/
├── src/
│   ├── pages/        → Login, Dashboard, NovoPedido, Historico, Estoque
│   ├── components/   → Navbar, Card
│   ├── services/     → api.ts, pedidoService.ts (chamadas HTTP)
│   ├── models/       → types.ts (interfaces TypeScript)
│   └── assets/       → Recursos estáticos
```

---

## Tecnologias

### Back-End
- Java 17
- Spring Boot 3.2.0
- Spring MVC
- Spring Data JPA / Hibernate
- H2 Database (em memória)
- Maven
- Lombok

### Front-End
- React 19
- Vite
- TypeScript
- Tailwind CSS 4
- Axios

---

## Banco de Dados

H2 em memória com as seguintes tabelas:

| Tabela        | Campos                                              |
|---------------|-----------------------------------------------------|
| `cliente`     | id, nome, email, senha                              |
| `pedido`      | id, cliente_id, data, sabor, quantidade, valor_total, status |
| `estoque`     | id, produto, quantidade                             |
| `movimentacao`| id, data, tipo, valor, descricao                    |

---

## Endpoints da API

### Autenticação
| Método | Rota                  | Descrição       |
|--------|-----------------------|-----------------|
| POST   | `/api/auth/login`     | Login do cliente |

### Clientes
| Método | Rota                  | Descrição            |
|--------|-----------------------|----------------------|
| GET    | `/api/clientes`       | Listar todos         |
| GET    | `/api/clientes/{id}`  | Buscar por ID        |

### Pedidos
| Método | Rota                                 | Descrição                  |
|--------|--------------------------------------|----------------------------|
| POST   | `/api/pedidos`                       | Criar pedido               |
| POST   | `/api/pedidos/{id}/estorno`          | Estornar pedido            |
| GET    | `/api/pedidos`                       | Histórico de todos pedidos |
| GET    | `/api/pedidos?clienteId={id}`        | Histórico de um cliente    |

### Estoque
| Método | Rota                  | Descrição       |
|--------|-----------------------|-----------------|
| GET    | `/api/estoque`        | Listar estoque  |

### Movimentações
| Método | Rota                       | Descrição            |
|--------|----------------------------|----------------------|
| GET    | `/api/movimentacoes`       | Listar movimentações |

---

## Entidades

### Cliente
```java
@Entity
public class Cliente {
    Long id;
    String nome;
    String email;     // usado no login
    String senha;     // usado no login
}
```

### Pedido
```java
@Entity
public class Pedido {
    Long id;
    Long clienteId;
    LocalDateTime data;
    String sabor;        // FRANGO, CATUPIRY ou CALABRESA
    Integer quantidade;
    Double valorTotal;
    String status;       // PENDENTE, CONFIRMADA, ESTORNADA, CANCELADA
}
```

### Estoque
```java
@Entity
public class Estoque {
    Long id;
    String produto;      // FRANGO, CATUPIRY, CALABRESA
    Integer quantidade;
}
```

### Movimentacao
```java
@Entity
public class Movimentacao {
    Long id;
    LocalDateTime data;
    String tipo;         // PEDIDO ou ESTORNO
    Double valor;
    String descricao;
}
```

---

## Design Patterns Implementados

### 1. Command

**Onde:** `com.sistema.coxinha.command`

**Responsabilidade:** Encapsular ações do sistema como objetos, permitindo executar e desfazer operações.

**Interface:**
```java
public interface Command {
    void executar();
}
```

**Implementações no sistema:**

| Classe                     | Função                                                   |
|----------------------------|----------------------------------------------------------|
| `RealizarPedidoCommand`    | Cria a coxinha via Factory, calcula preço via Strategy, valida/baixa estoque, salva Pedido e registra Movimentação |
| `EstornarPedidoCommand`    | Localiza o Pedido, valida transição de estado via State, repõe estoque, atualiza status e registra Movimentação de estorno |

**Fluxo de uso:**
1. `PedidoService.realizarPedido()` constrói um `RealizarPedidoCommand` via Builder e chama `executar()`
2. `PedidoService.estornarPedido()` constrói um `EstornarPedidoCommand` via Builder e chama `executar()`

---

### 2. Strategy

**Onde:** `com.sistema.coxinha.strategy`

**Responsabilidade:** Definir uma família de algoritmos para cálculo de preço, encapsulando cada um e tornando-os intercambiáveis.

**Interface:**
```java
public interface CalculoPrecoStrategy {
    Double calcular(Double precoBase);
}
```

**Implementações no sistema:**

| Classe            | Comportamento                                      |
|-------------------|----------------------------------------------------|
| `PrecoPadrao`     | Retorna o preço base sem alteração (preço cheio)   |
| `PrecoPromocional`| Aplica 10% de desconto (precoBase * 0.9)           |

**Seleção da estratégia:**
O cliente marca um checkbox "Usar cupom promocional" no formulário de pedido. O `PedidoService` então escolhe qual estratégia injetar no `RealizarPedidoCommand`:

```java
CalculoPrecoStrategy strategy = usarCupom ? precoPromocional : precoPadrao;
```

**Fluxo de uso:**
1. `PedidoService.realizarPedido()` seleciona a estratégia baseada no parâmetro `usarCupom`
2. A estratégia é passada para o `RealizarPedidoCommand` via Builder
3. O comando chama `precoStrategy.calcular(coxinha.getPrecoBase())` para obter o preço unitário
4. Multiplica pela quantidade para obter o valor total do pedido

---

### 3. Factory Method

**Onde:** `com.sistema.coxinha.factory`

**Responsabilidade:** Fornecer uma interface para criar objetos de coxinha sem especificar a classe concreta. Nenhuma classe cria coxinhas usando `new` diretamente.

**Interface do produto:**
```java
public interface Coxinha {
    String getSabor();
    Double getPrecoBase();
}
```

**Produtos concretos:**

| Classe              | Sabor     | Preço Base |
|---------------------|-----------|------------|
| `CoxinhaFrango`     | FRANGO    | R$ 5,00    |
| `CoxinhaCatupiry`   | CATUPIRY  | R$ 6,00    |
| `CoxinhaCalabresa`  | CALABRESA | R$ 5,50    |

**Factory:**
```java
@Component
public class CoxinhaFactory {
    public Coxinha criarCoxinha(String sabor) {
        switch (sabor.toUpperCase()) {
            case "FRANGO":    return new CoxinhaFrango();
            case "CATUPIRY":  return new CoxinhaCatupiry();
            case "CALABRESA": return new CoxinhaCalabresa();
            default: throw new IllegalArgumentException("Sabor desconhecido");
        }
    }
}
```

**Fluxo de uso:**
1. `RealizarPedidoCommand` chama `coxinhaFactory.criarCoxinha(sabor)`
2. A factory retorna a instância correta baseada no sabor
3. O comando obtém `getPrecoBase()` para calcular o valor

---

### 4. Observer

**Onde:** `com.sistema.coxinha.observer`

**Responsabilidade:** Definir uma dependência um-para-muitos entre objetos, de forma que quando um objeto muda de estado, todos os seus dependentes são notificados automaticamente.

**Interface:**
```java
public interface EstoqueObserver {
    void atualizar(String produto, Integer quantidade);
}
```

**Implementações no sistema:**

| Classe                  | Comportamento                                                    |
|-------------------------|------------------------------------------------------------------|
| `LogEstoqueObserver`    | Registra no console toda alteração de estoque; emite ALERTA se quantidade < 5 |
| `PainelEstoqueObserver` | Simula atualização de um painel em tempo real no console          |

**Fluxo de uso:**
1. `EstoqueService` mantém uma lista de `EstoqueObserver` (injetada pelo Spring)
2. Após cada alteração de estoque (`baixarEstoque` / `reporEstoque`), chama `notificarObservers(estoque)`
3. Cada observer recebe `atualizar(produto, quantidade)` e reage conforme sua implementação

---

### 5. State

**Onde:** `com.sistema.coxinha.state`

**Responsabilidade:** Permitir que um objeto altere seu comportamento quando seu estado interno muda, parecendo mudar de classe.

**Interface:**
```java
public interface PedidoState {
    String getStatus();
    void confirmar(PedidoContext context);
    void estornar(PedidoContext context);
    void cancelar(PedidoContext context);
}
```

**Contexto:**
```java
public class PedidoContext {
    private PedidoState state;  // Inicia como PendenteState
    // métodos: confirmar(), estornar(), cancelar(), getStatus()
}
```

**Estados e Transições Válidas:**

```
PENDENTE ──confirmar──► CONFIRMADA ──estornar──► ESTORNADA
    │                       │
    └──cancelar──► CANCELADA  └── (estornar apenas)
```

| Estado          | Pode confirmar? | Pode estornar? | Pode cancelar? |
|-----------------|:---------------:|:--------------:|:--------------:|
| `PendenteState` |       SIM       |      NÃO       |      SIM       |
| `ConfirmadaState`|      NÃO       |      SIM       |      NÃO       |
| `EstornadaState`|      NÃO       |      NÃO       |      NÃO       |
| `CanceladaState`|      NÃO       |      NÃO       |      NÃO       |

**Fluxo de uso:**
1. `RealizarPedidoCommand`: cria `PedidoContext` (estado inicial PENDENTE), chama `confirmar()` → transita para CONFIRMADA, persiste o status no Pedido
2. `EstornarPedidoCommand`: cria `PedidoContext` com status atual do Pedido, chama `estornar()` → valida a transição, transita para ESTORNADA, atualiza o Pedido

---

## Tratamento de Exceções

`GlobalExceptionHandler` (`@RestControllerAdvice`) captura exceções e retorna JSON padronizado:

```json
{
  "sucesso": false,
  "mensagem": "Descrição do erro"
}
```

---

## Fluxos Principais

### Realizar Pedido
1. Cliente faz login (`POST /api/auth/login`)
2. Cliente seleciona sabor, informa quantidade e opcionalmente marca "Usar cupom promocional" no formulário
3. Controller recebe JSON com `clienteId`, `sabor`, `quantidade`, `usarCupom`
4. `RealizarPedidoCommand` é executado:
   - Factory cria a coxinha do sabor escolhido
   - Strategy calcula o valor (PrecoPadrao ou PrecoPromocional se cupom ativo)
   - Estoque é validado e baixado
   - State confirma o pedido (PENDENTE → CONFIRMADA)
   - Pedido é persistido
   - Movimentação financeira é registrada
5. JSON de sucesso é retornado

### Estornar Pedido
1. Cliente solicita estorno na tela de histórico
2. `EstornarPedidoCommand` é executado:
   - Pedido é localizado
   - State valida transição (deve estar CONFIRMADA)
   - Estoque é reposto
   - Status muda para ESTORNADA
   - Movimentação de estorno é registrada
3. Pedido some da lista ativa

### Visualizar Perfil
1. Cliente acessa a aba "Perfil" no menu
2. Requisição `GET /api/clientes/{id}` é feita
3. Dados do cliente são exibidos (somente leitura): nome, email, ID

### Visualizar Histórico Próprio
1. Cliente acessa a aba "Histórico"
2. Requisição `GET /api/pedidos?clienteId={id}` filtra apenas os pedidos do cliente logado
3. Tabela exibe ID, Data, Sabor, Quantidade, Valor Total, Status e botão "Estornar"

---

## Dados de Teste

O `DataInitializer` popula automaticamente o banco ao iniciar:

**Clientes:**
| Nome  | Email             | Senha   |
|-------|-------------------|---------|
| Davi  | davi@email.com    | 123456  |
| Maria | maria@email.com   | 123456  |

**Estoque inicial:** 10 unidades de cada sabor (FRANGO, CATUPIRY, CALABRESA)

---

## Como Executar

1. Execute o script `INICIAR_SISTEMA.bat` ou abra dois terminais:

**Terminal 1 (Back-End):**
```bash
cd backend
mvn spring-boot:run
```
O backend iniciará em `http://localhost:8080`

**Terminal 2 (Front-End):**
```bash
cd frontend
npm install
npm run dev
```
O frontend iniciará em `http://localhost:3000`

3. Acesse `http://localhost:3000` e faça login com `davi@email.com` / `123456`
