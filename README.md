# Sobre o Sistema - Barber-Manager

## 1. Introdução

Barber-Manager é um sistema desenvolvido para facilitar a gestão de uma barbearia, atendendo às necessidades de administração e registro de vendas. Os stakeholders incluem o administrador da barbearia, responsável pela gestão dos recursos, e os barbeiros, que registram as vendas associadas aos serviços prestados.

## 2. Funcionalidades

### 2.1. Funcionalidades do Administrador
O administrador tem acesso a várias ferramentas de gestão, incluindo:
- **Gestão de Profissionais**: Inserção, edição e remoção de barbeiros, que podem ser vinculados a registros de venda.
- **Gestão de Serviços**: Inserção, edição e remoção dos serviços oferecidos, como cortes de cabelo e barbas, que podem ser vinculados a registros de venda.
- **Gestão de Métodos de Pagamento**: Inserção, edição e remoção dos métodos de pagamento aceitos, possibilitando uma maior flexibilidade na venda.
- **Visualização de Vendas**: Acesso a relatórios e registros de vendas realizadas, permitindo que o administrador acompanhe o faturamento e o desempenho dos profissionais.

### 2.2. Funcionalidades do Barbeiro
Os barbeiros são responsáveis por registrar as vendas de serviços oferecidos. Cada venda contém:
- **Inserção de Vendas**: Registro do serviço realizado, profissional responsável, método de pagamento e data de execução. Esse registro facilita o acompanhamento do faturamento e dos serviços prestados.

## 3. Tecnologias Utilizadas

Para garantir robustez, segurança e escalabilidade, as seguintes tecnologias foram utilizadas no desenvolvimento do sistema:

- **Java**: Linguagem de programação usada para o desenvolvimento do backend, garantindo robustez e desempenho.
- **JSP (JavaServer Pages)**: Usadas para a camada de apresentação (interface do usuário), proporcionando uma interface dinâmica e eficiente.
- **Servlets**: Responsáveis pelo controle de fluxo de dados entre as páginas JSP e o backend, implementando a lógica de negócio do sistema.
- **DTOs (Data Transfer Objects)**: Implementados para a transferência de dados entre a camada de apresentação e a camada de persistência, otimizando o desempenho e a organização do código.
- **DAOs (Data Access Objects)**: Utilizados para gerenciar o acesso e a manipulação dos dados no banco, separando a lógica de persistência da lógica de aplicação.
- **JDBC (Java Database Connectivity)**: Utilizado para acessar e manipular o banco de dados MySQL.
- **MySQL**: Banco de dados relacional escolhido para persistência de dados, com escalabilidade e confiabilidade adequadas para o sistema.

## 4. Arquitetura do Sistema

A aplicação segue o padrão de arquitetura **MVC (Model-View-Controller)**, que separa as responsabilidades entre as camadas de Model (representação dos dados e lógica de negócio), View (camada de apresentação) e Controller (controle de fluxo e lógica de aplicação). Essa separação facilita a manutenção e a escalabilidade do sistema.

## 5. Principais Dificuldades Encontradas

Durante o desenvolvimento do sistema, enfrentamos algumas dificuldades, incluindo:
- **Incompatibilidade de Versões de Jakarta**: Algumas bibliotecas apresentaram incompatibilidades devido a diferentes versões de Jakarta, o que exigiu ajustes para garantir a execução correta do sistema.
- **Execução em Máquinas Diferentes**: Variáveis de configuração e dependências causaram dificuldades ao executar o sistema em diferentes ambientes, o que exigiu ajustes nas configurações.
- **Problemas com Tomcat e Conexão ao Banco de Dados**: O servidor Tomcat apresentou instabilidade ao lidar com a conexão ao banco de dados MySQL, o que demandou atenção à configuração do servidor e das conexões.

Devido a essas dificuldades, foi necessário iniciar um novo projeto do zero para assegurar a execução correta nas máquinas dos dois colaboradores do projeto. O link para o projeto antigo está disponível a seguir:

**Link para o projeto antigo:** https://github.com/Marcelo13104/ManagerBarber

