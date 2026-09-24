# TaskUp API

Sistema de controle de estoque e vendas desenvolvido pela squad **Mousseomanos**, como projeto da disciplina de Multiplataforma e Ágil.

## 💡 Sobre o projeto

O TaskUp permite:
- Cadastrar, consultar, editar e inativar produtos
- Controlar quantidade em estoque (entrada e ajuste)
- Alertar sobre produtos com estoque baixo
- (Em construção) Registrar vendas com baixa automática no estoque

## 🛠️ Tecnologias

**Back-end**
- Java 17
- Spring Boot
- Spring Data JPA
- Banco H2 (em memória, para desenvolvimento local)

**Front-end**
- Angular

## 🚀 Como rodar o back-end localmente

1. Clone o repositório
2. Abra o projeto no Eclipse (ou outra IDE de sua preferência)
3. Rode a classe `TaskUpApplication.java`
4. A API sobe em `http://localhost:8080`
5. Console do banco H2 disponível em `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:testdb`
   - Usuário: `sa` | Senha: (em branco)

## 📚 Endpoints da API — Produto

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/produtos` | Cadastrar produto |
| GET | `/produtos` | Listar todos os produtos |
| GET | `/produtos/{id}` | Buscar produto por id |
| GET | `/produtos?nome=` | Pesquisar produto por nome |
| PUT | `/produtos/{id}` | Atualizar produto |
| PATCH | `/produtos/{id}/estoque` | Ajustar quantidade em estoque |
| PATCH | `/produtos/{id}/inativar` | Inativar produto |
| GET | `/produtos/estoque-baixo` | Listar produtos com estoque baixo |

## 🌳 Fluxo de trabalho da squad

Cada funcionalidade é desenvolvida em uma branch própria (`feature/nome-da-task`), com commits, push e Pull Request antes de mesclar no `main`.
