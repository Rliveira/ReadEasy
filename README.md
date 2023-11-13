# Sistema de livraria

## Integrantes do grupo:
- João Victor Morais Barreto da Silva - Joao.moraissilva@ufrpe.br
- Rony Elias de Oliveira - r.eliasemp@gmail.com
- Louise de Athayde Vieira - lvathayd@gmail.com
- Mariane Elisa dos Santos Souza - mariane.elisa@ufrpe.br

## Descrição:
  - O sistema oferece recursos de controle de acesso, venda de livros, histórico de compras e vendas, relatório de vendas e gerenciamento de livros, perfis e estoque, atendendo às necessidades dos funcionários e da livraria.

### Perguntas e respostas:

1. **Quem vai usar o programa?**
    - R: Funcionários e administradores/gerentes da Livraria.

<br>

2. **Que serviços são importantes para os usuários?**
    - R: Os serviços importantes são:  
      - Cadastro de Livros
      - registro de vendas
      - registro de compras do fornecedor
      - registro e a atualização do estoque de livros

<br>

3. **Qual a principal funcionalidade do seu sistema?**
    - R: Registrar e gerenciar a venda livros de uma livraria.

<br>

4. **Quais serviços cada usuário pode executar?**
    - R: Funcionários comuns podem:
      - Registrar vendas
      - Cadastrar, editar e deletar livros
      - Atualizar o estoque de livros

    - Administradores:
      - Gerenciar perfis que acessarão o sistema (adicionar, editar, remover)
      - Gerenciar os fornecedores da empresa (adicionar, editar, remover)
      - Visualizar o histórico de compras dos fornecedores e histórico de vendas dos clientes
      - Executar todas as funcionalidades que um funcionário comum pode executar

# Requisitos:

**REQ 1 - Gerenciamento de perfis:**
 	
  - O sistema deve possibilitar o gerenciamento (Create, Recover, Update e Delete - CRUD)  e perfis que acessarão o sistema.

<br>

**REQ 2 - Controle de Acesso:**
  - O sistema deve implementar um controle de acesso baseado em perfis de usuário. Os usuários serão categorizados em dois tipos, o administrador e funcionários, garantindo que cada perfil tenha acesso apenas às funcionalidades autorizadas para sua respectiva função.

<br>

**REQ 3 - Venda de Livros e Histórico de compras dos clientes:**
  - O sistema deve permitir a venda de livros previamente cadastrados e salvá-los como em um histórico de vendas.

<br>

**REQ 4 - Relatório de Vendas dos Livros:**
  - O sistema deve permitir um relatório das vendas de cada livro vendido, informando a quantidade vendida, o valor de lucro obtido e uma classificação dos livros mais vendidos e que mais geraram lucro para a livraria.

<br>

**REQ 5 - Gerenciamento de Livros e estoque:** 
  - O sistema deve permitir o gerenciamento do estoque e CRUD dos Livros cadastrados.

<br>

**REQ 6 - Gerenciamento de fornecedores, histórico de compras e vendas da livraria:** 
  - O sistema deve permitir o gerenciamento CRUD dos fornecedores da livraria, além de registrar e listar todas as compras realizadas dos fornecedores.
