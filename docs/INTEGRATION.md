# Guia de Integração - MatchCarreira API

Este documento contém as especificações técnicas para integração do Front-End com o ecossistema **Elkys**.

---

## 🔗 Base URL
- **Local:** `http://localhost:8080`
- **CORS:** Habilitado para todas as origens (ambiente de desenvolvimento).

---

## 🔑 Autenticação (JWT)
A API utiliza **Bearer Token**. O fluxo de segurança segue o padrão:

1. **Obtenção do Token**: Enviar credenciais para `POST /auth/login`.
2. **Uso do Token**: Incluir o token em todas as requisições protegidas via Header:
   ```http
   Authorization: Bearer <seu_jwt_aqui>
   ```

# 📂 Endpoints Principais

### 1. Autenticação (`/auth`)

| Método | Endpoint | Descrição | Acesso |
| :--- | :--- | :--- | :--- |
| **POST** | `/auth/login` | Realiza login e retorna o Token JWT | Público |
| **POST** | `/auth/esqueci-senha` | Solicita e-mail de recuperação de acesso | Público |
| **POST** | `/auth/redefinir-senha` | Atualiza a senha utilizando o token recebido | Público |

### 2. Gestão de Usuários (`/usuarios`)

| Método | Endpoint | Descrição | Acesso |
| :--- | :--- | :--- | :--- |
| **POST** | `/usuarios` | Cadastro de nova conta (Onboarding) | Público |
| **GET** | `/usuarios` | Listagem de usuários (Administrativo) | Privado |

### 3. Perfil e Currículo (`/meu-perfil`)

| Método | Endpoint | Descrição | Acesso |
| :--- | :--- | :--- | :--- |
| **GET** | `/meu-perfil` | Retorna o Perfil Completo (Currículo + Dados) | Privado |
| **PUT** | `/meu-perfil` | Atualiza dados básicos (Cidade, Estado, etc.) | Privado |
| **POST** | `/meu-perfil/experiencias` | Adiciona nova Experiência Profissional | Privado |
| **POST** | `/meu-perfil/formacoes` | Adiciona nova Formação Acadêmica | Privado |
| **POST** | `/meu-perfil/competencias` | Atualiza a lista de Skills/Competências | Privado |

---

## ⚠️ Padrão de Erros

A API utiliza respostas padronizadas para facilitar o tratamento no Front-End e garantir a transparência do fluxo.



### Erros de Validação (400 Bad Request)
Quando campos obrigatórios falham na validação (**Bean Validation**), o retorno é uma lista de erros detalhando o campo afetado:

```json
[
  {
    "campo": "email",
    "mensagem": "formato de e-mail inválido"
  },
  {
    "campo": "senha",
    "mensagem": "deve ter pelo menos 6 caracteres"
  }
]
```
### ⚠️ Erros de Regra de Negócio ou Genéricos

Para erros disparados pela lógica da aplicação (ex: e-mail já cadastrado) ou exceções internas do servidor, o retorno segue este formato simplificado:

```json
{
  "mensagem": "Descrição amigável do erro ou regra de negócio violada"
}
```
## 📖 Documentação Interativa (Swagger)

Para visualizar os modelos de dados (JSON) detalhados, esquemas de entrada e realizar testes em tempo real, utilize nossa interface interativa:

<div align="center">
  <br />
  <a href="http://localhost:8080/swagger-ui.html" target="_blank">
    <img src="https://img.shields.io/badge/ACESSAR_SWAGGER_UI-85EA2D?style=for-the-badge&logo=swagger&logoColor=black" alt="Swagger UI" />
  </a>
  <br />

</div>

