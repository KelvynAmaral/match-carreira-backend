# MatchCarreira API

O **MatchCarreira** é o motor de uma plataforma de aceleração de carreira. Desenvolvido para ser resiliente e escalável, o sistema utiliza containerização e automação de banco de dados para garantir paridade entre os ambientes de desenvolvimento e produção.

<div align="center">

### 🛠 Tech Stack

![Java](https://img.shields.io/badge/Java_21-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/Spring_Boot_3.2-6DB33F?style=flat-square&logo=spring&logoColor=white)
![JWT](https://img.shields.io/badge/JWT_Security-black?style=flat-square&logo=jsonwebtokens&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=flat-square&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker_Compose-2496ED?style=flat-square&logo=docker&logoColor=white)
![Swagger](https://img.shields.io/badge/OpenAPI_3-85EA2D?style=flat-square&logo=swagger&logoColor=black)

</div>

## Infraestrutura com Docker
A aplicação está pronta para rodar em ambientes isolados, garantindo que o banco de dados e as dependências subam corretamente com um único comando.

```mermaid
    
flowchart TD
    Client[Clientes Web/Mobile] --> API[Spring Boot API]
    API --> Auth[Spring Security + JWT]
    API --> Services[Serviços / Regras de Negócio]
    Services --> DB[(PostgreSQL)]
    
    subgraph Infra ["Infraestrutura"]
    DB
    Docker[Docker / Compose]
    end

    %% Definição de Classes de Estilo
    classDef interface fill:#e1f5fe,stroke:#01579b,stroke-width:2px,color:#01579b;
    classDef logic fill:#fff3e0,stroke:#e65100,stroke-width:2px,color:#e65100;
    classDef security fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#2e7d32;
    classDef data fill:#ede7f6,stroke:#4527a0,stroke-width:2px,color:#4527a0;
    classDef infra fill:#f5f5f5,stroke:#424242,stroke-dasharray: 5 5;

    %% Aplicação das Classes
    class Client interface;
    class API,Services logic;
    class Auth security;
    class DB data;
    class Infra infra;
    
```

## 🏗️ Arquitetura e Engenharia

A arquitetura do **MatchCarreira** foi refatorada para eliminar a desordem técnica, agrupando responsabilidades por contexto de negócio (Domain-Driven Design simplificado):

| Contexto | Responsabilidade |
| :--- | :--- |
| 🔐 **auth** | Autenticação robusta e Recuperação de Senha. |
| 👤 **perfil** | Gestão de Currículos, Experiências e Formações. |
| ⚙️ **usuário** | Gestão de Contas e preferências do sistema. |

---

## Boas Práticas e Diferenciais Técnicos


#### Refinamento de Código
* **Padrão DTO:** Separação entre *Solicitação* (entrada) e *Resposta* (saída) para segurança dos dados.
* **Imutabilidade com Records:** Uso de *Java Records* para garantir objetos concisos e thread-safe.

#### Infraestrutura Integrada
* **Auditoria Automática:** Rastreabilidade total com campos `criado_em` e `atualizado_em` via JPA.
* **CORS & Segurança:** Configuração pronta para integração com front-ends modernos.

---
## ☁️ Cloud-Ready Development

O **MatchCarreira** está totalmente preparado para o desenvolvimento em nuvem. Graças ao **GitHub Codespaces** e **DevContainers**, você pode rodar todo o ecossistema (API + PostgreSQL + Redis) diretamente no seu navegador, eliminando a "desordem técnica" de configuração local.

<div align="center">

[![Open in GitHub Codespaces](https://img.shields.io/static/v1?style=for-the-badge&label=GitHub+Codespaces&message=Abrir+Projeto&color=24292e&logo=github)](https://github.com/codespaces/new?hide_repo_select=true&ref=main&repo=KelvynAmaral/match-carreira-backend)

</div>


## 📖 Documentação da API

Com a aplicação em execução via **Docker**, a interface interativa fica disponível para testes imediatos:

[ ![Swagger](https://img.shields.io/badge/Acessar_Swagger_UI-85EA2D?style=for-the-badge&logo=swagger&logoColor=black) ](http://localhost:8080/swagger-ui.html)