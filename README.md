# **API de Publicação de Livros**

Este repositório contém uma API de publicação de livros desenvolvida utilizando Java 17, Spring Boot e React para o frontend. API oferece funcionalidades de criação, atualização e deleção (CRUD) para livros, bem como o upload e download de arquivos associados aos livros.

## Tecnologias Utilizadas

### Backend (API)
- Java 17
- Spring Boot
- JPA (Hibernate)
- Swagger (para documentação da API)

### Frontend
- React
- Axios (para chamadas HTTP)

## Requisitos

Antes de começar, certifique-se de ter as seguintes ferramentas instaladas em seu ambiente de desenvolvimento:

- Java 17 ou superior
- Maven ou Gradle (para gerenciamento de dependências e construção do projeto Spring Boot)
- Node.js (incluindo o npm)
- Git (para clonagem do repositório)
- IDE (como IntelliJ IDEA, VS Code, ou Eclipse)

## Passos para Rodar a API e o Frontend

1. **Clonando o Repositório**  
   Clone este repositório em sua máquina local utilizando o seguinte comando:

   ```bash
   git clone https://github.com/Felipejf05/proj-facul.git

## Subindo a API

Para rodar a API com Spring Boot, siga os seguintes passos:

1. Abra o projeto da API em sua IDE (IntelliJ IDEA, VS Code, etc)

2. Compile e inicie o servidor da API.

A API estará rodando localmente no http://localhost:8080.

## Configurando o Frontend (React)

Instalando Dependências:

Após configurar o backend, vamos configurar o frontend:

1. Navegue até a pasta do frontend (/frontend):

cd frontend

2. Abra o terminal dentro da pasta (frontend) e instale as dependências usando o npm:

npm install

## Subindo o Frontend
Agora, para rodar o frontend:

Clique com o botão direito na pasta frontend e abra um terminal.

Execute o seguinte comando para iniciar o servidor de desenvolvimento:

npm start

O frontend será iniciado e estará disponível em http://localhost:3000.

## Acessando a API e Interagindo com o Frontend

API (Backend): A interação com a API pode ser feita diretamente através dos endpoints definidos no Swagger UI, acessível em http://localhost:8080/swagger-ui/index.html

Frontend (React): O frontend estará acessível em http://localhost:3000, onde podemos interagir com a interface gráfica para criar e gerenciar usuários e livros.

## Endpoints da API

Usuários

GET /v1/users/list-users – Lista todos os usuários
GET /v1/users/{id} – Obtém um usuário específico por ID
POST /v1/users – Cria um novo usuário
PUT /v1/users/{id} – Atualiza um usuário existente
DELETE /v1/users/{id} – Deleta um usuário específico
POST /v1/users/login – Realiza o login de um usuário

Livros

GET /v1/list-books – Lista todos os livros
GET /v1/books/{id} – Obtém um livro específico por ID
POST /v1/books – Adiciona um novo livro
PUT /v1/books/{id} – Atualiza um livro existente
DELETE /v1/books/{id} – Deleta um livro específico
POST /v1/books/{id}/upload – Faz upload de um arquivo associado a um livro
GET /v1/books/{id}/download – Faz download do arquivo associado a um livro
DELETE /v1/books/{id}/delete-file – Deleta o arquivo associado a um livro