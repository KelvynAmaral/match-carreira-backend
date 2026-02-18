# MatchCarreira API - Elkys

O **MatchCarreira** é o motor de uma plataforma de aceleração de carreira. Desenvolvido para ser resiliente e escalável, o sistema utiliza containerização e automação de banco de dados para garantir paridade entre os ambientes de desenvolvimento e produção.

## 🚀 DNA Técnico & Infraestrutura
- **Linguagem:** Java 21+ (Spring Boot 3.2+)
- **Segurança:** Spring Security + JWT (Stateless)
- **Banco de Dados:** PostgreSQL (Produção) / H2 (Testes)
- **Containerização:** Docker & Docker Compose
- **Documentação:** Swagger/OpenAPI 3

## 🏗️ Infraestrutura com Docker
A aplicação está pronta para rodar em ambientes isolados, garantindo que o banco de dados e as dependências subam corretamente com um único comando.

### Requisitos
- Docker
- Docker Compose

### Como subir o ambiente
Na raiz do projeto, execute:
```bash
docker-compose up -d
```
Isso subirá o container do **PostgreSQL** e a aplicação **MatchCarreira** de forma orquestrada.

### 📂 Organização por Domínios
A arquitetura foi refatorada para eliminar o *clutter* técnico, agrupando responsabilidades por contexto de negócio:

* **auth**: Autenticação e Recuperação de Senha.
* **perfil**: Currículos, Experiências e Formações.
* **usuario**: Gestão de contas.

### 🛠️ Boas Práticas Implementadas
* **DTO Pattern**: Separação rigorosa entre *Request* (entrada) e *Response* (saída).
* **CORS**: Configurado para integração com front-ends modernos.
* **Auditoria**: Campos `criado_em` e `atualizado_em` automáticos via JPA.
* **Clean Code**: Uso de *Records* para imutabilidade e concisão.

### 🛡️ Documentação de API
Com a aplicação rodando, acesse a interface interativa para testes:
> 🔗 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
