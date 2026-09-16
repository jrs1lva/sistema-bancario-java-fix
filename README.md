# 🏦 Sistema Bancário - Console Application

Um sistema bancário robusto desenvolvido em **Java**, projetado para simular operações financeiras reais operando diretamente via console. O projeto tem como foco principal a aplicação de **Engenharia de Software**, **Orientação a Objetos (POO)** e **Clean Code**, com uma arquitetura preparada para escalabilidade e persistência em banco de dados.

## 🚀 Funcionalidades

- **Gestão de Usuários:** 
  - Validação matemática de autenticidade de CPF na camada de domínio.
  - Bloqueio de cadastro para menores de 18 anos.
- **Contas Bancárias (Corrente e Poupança):**
  - **Conta Corrente:** Gestão inteligente de cheque especial (limite padrão de R$ 500,00) com amortização automática de dívidas durante depósitos.
  - **Conta Poupança:** Aplicação de rendimento automático de juros (2%) a cada novo depósito realizado.
- **Operações Financeiras:**
  - Saques e depósitos blindados contra valores negativos e insuficientes através de lógica *Fail-Fast* (lançamento de Exceções).
  - Emissão de comprovantes detalhados das transações.
  - Histórico de transações (Extrato).
- **Interface Interativa (CLI):**
  - Navegação fluida via menus no terminal.
  - Limpeza de tela e pausas dinâmicas (`Thread.sleep`) para uma imersão superior do usuário.

## 🛠️ Tecnologias e Arquitetura

- **Linguagem:** Java
- **Testes:** JUnit 5 (Cobertura de testes unitários validando as regras de negócio essenciais).
- **Arquitetura MVC:** Separação clara entre Lógica de Negócio (`model`), Utilitários (`util`), e Interação com o Usuário (`view`).
- **Design de Código:** Aplicação de Herança (classes abstratas), Polimorfismo e Encapsulamento de dados.

## ⚙️ Como Executar o Projeto

1. **Clone o repositório para a sua máquina:**
   ```bash
   git clone [https://github.com/jrs1lva/sistema-bancario-java-fix.git](https://github.com/jrs1lva/sistema-bancario-java-fix.git)
   ```
2. **Importe o projeto** na sua IDE de preferência (Eclipse, IntelliJ IDEA, VS Code).
3. **Execute a aplicação** rodando a classe principal do projeto para inicializar o `Menu` no terminal.

## 🧪 Testes Unitários

O núcleo da aplicação possui testes automatizados para garantir a integridade dos dados:
- Validação de exceções usando `assertThrows`.
- Isolamento de testes com a anotação `@BeforeEach`.
- Captura e redirecionamento de `System.out` para validação de comprovantes gerados no console.

## 🗺️ Roadmap (Próximos Passos)

O projeto está em evolução ativa para adoção de persistência real de dados:
- [ ] **Integração com Banco de Dados:** Substituir o armazenamento temporário em memória (`ArrayList`) pelo banco de dados relacional **PostgreSQL**.
- [ ] **Padrão DAO (Data Access Object):** Criação das classes `UsuarioDAO` e `ContaDAO` para isolar completamente as instruções SQL (CRUD) da camada de domínio.
- [ ] **Testes de Integração com BD:** Expansão da suíte de testes com JUnit 5 para validar as conexões, utilizando `@AfterEach` para garantir o fechamento seguro do *pool* de conexões.

---
*Desenvolvido com foco no aprofundamento em arquitetura de software de backend e boas práticas do mercado.*
