# Guia de Integração - MatchCarreira API

Este documento contém as especificações técnicas para integração do Front-End com o ecossistema **Elkys**.

---

## 🔗 Base URL
- **Local:** `http://localhost:8080`
- **CORS:** Habilitado para todas as origens (em desenvolvimento).

---

## 🔑 Autenticação (JWT)
A API utiliza **Bearer Token**. O fluxo de segurança segue o padrão:

1. **Obtenção do Token**: Enviar credenciais para `POST /auth/login`.
2. **Uso do Token**: Incluir o token em todas as requisições protegidas via Header:
   ```http
   Authorization: Bearer <seu_jwt_aqui>

### 📂 Endpoints Principais

#### 1. Autenticação (`/auth`)
| Método | Endpoint | Descrição | Acesso |
| :--- | :--- | :--- | :--- |
| POST | `/auth/login` | Login e retorno do Token | Público |
| POST | `/auth/esqueci-senha` | Solicita e-mail de recuperação | Público |
| POST | `/auth/redefinir-senha` | Atualiza senha com o token recebido | Público |

#### 2. Gestão de Usuários (`/usuarios`)
| Método | Endpoint | Descrição | Acesso |
| :--- | :--- | :--- | :--- |
| POST | `/usuarios` | Cadastro de nova conta | Público |
| GET | `/usuarios` | Listagem de usuários cadastrados | Privado |

#### 3. Perfil e Currículo (`/meu-perfil`)
| Método | Endpoint | Descrição | Acesso |
| :--- | :--- | :--- | :--- |
| GET | `/meu-perfil` | Retorna o Perfil Completo | Privado |
| PUT | `/meu-perfil` | Atualiza dados básicos (Cidade, Estado, etc) | Privado |
| POST | `/meu-perfil/experiencias` | Adiciona Experiência Profissional | Privado |
| POST | `/meu-perfil/formacoes` | Adiciona Formação Acadêmica | Privado |

---

### ⚠️ Padrão de Erros
Todas as validações de regra de negócio retornam erro **400 (Bad Request)** com o seguinte formato de corpo:

```json
{
  "mensagem": "Descrição amigável do erro",
  "data_hora": "2026-02-18T15:30:00"
}

```

### Documentação Interativa (Swagger)
Para visualizar os modelos de dados (JSON) de entrada e saída, acesse:
http://localhost:8080/swagger-ui.html