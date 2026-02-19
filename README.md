# MatchCarreira API

O **MatchCarreira** é o motor de uma plataforma de aceleração de carreira. Desenvolvido para ser resiliente e escalável, o sistema utiliza containerização, automação de banco de dados e cache em memória para garantir alta performance no processamento de perfis profissionais.

<div align="center">

### 🛠 Tech Stack

![Java](https://img.shields.io/badge/Java_21-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/Spring_Boot_3.4-6DB33F?style=flat-square&logo=spring&logoColor=white)
![Redis](https://img.shields.io/badge/Redis_Cache-DC382D?style=flat-square&logo=redis&logoColor=white)
![JWT](https://img.shields.io/badge/JWT_Security-black?style=flat-square&logo=jsonwebtokens&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=flat-square&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker_Compose-2496ED?style=flat-square&logo=docker&logoColor=white)
![Swagger](https://img.shields.io/badge/OpenAPI_3-85EA2D?style=flat-square&logo=swagger&logoColor=black)

</div>

## Infraestrutura e Fluxo de Dados
A aplicação utiliza **Redis** para cache de perfis, reduzindo drasticamente a carga no banco de dados em operações de leitura frequentes.

```mermaid
flowchart TD
    Client[Clientes Web/Mobile] --> API[Spring Boot API]
    API --> Auth[Spring Security + JWT]
    API --> Cache{Redis Cache}
    Cache -- Miss --> Services[Serviços / Regras de Negócio]
    Services --> DB[(PostgreSQL)]
    Cache -- Hit --> API
    
    subgraph Infra ["Infraestrutura"]
    DB
    Redis[Redis Server]
    Docker[Docker / Compose]
    end

    classDef interface fill:#e1f5fe,stroke:#01579b,stroke-width:2px,color:#01579b;
    classDef logic fill:#fff3e0,stroke:#e65100,stroke-width:2px,color:#e65100;
    classDef security fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#2e7d32;
    classDef data fill:#ede7f6,stroke:#4527a0,stroke-width:2px,color:#4527a0;
    classDef cache fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#c62828;
    classDef infra fill:#f5f5f5,stroke:#424242,stroke-dasharray: 5 5;

    class Client interface;
    class API,Services logic;
    class Auth security;
    class DB data;
    class Cache,Redis cache;
    class Infra infra;
    
```

## 🏗️ Arquitetura e Engenharia

A arquitetura do **MatchCarreira** agrupa responsabilidades por contexto de negócio (*Bounded Contexts*):

| Contexto | Responsabilidade |
| :--- | :--- |
| 🔐 **auth** | Autenticação JWT, Registro e Recuperação de Senha. |
| 👤 **perfil** | Gestão de Currículos, Experiências, Formações e Competências. |
| ⚙️ **usuário** | Gestão de Contas e automação de criação de perfil. |

---

## Boas Práticas e Diferenciais Técnicos

#### Performance e Cache
* **Redis Integration:** Cache estratégico de perfis utilizando as anotações `@Cacheable` e `@CacheEvict`.
* **Serialização Customizada:** Suporte total a tipos `java.time` (como `LocalDate`) no Redis através da configuração do módulo `jsr310`.

####  Refinamento de Código
* **Imutabilidade com Records:** Uso de *Java Records* para a criação de DTOs concisos, seguros e imutáveis.
* **Global Exception Handling:** Tratamento centralizado de exceções (erros 404, 403, 409) para garantir respostas padronizadas e limpas.

* **Criação Reativa:** Ao realizar o cadastro de um usuário, o sistema gera automaticamente a estrutura inicial de seu currículo.
* **Padronização Flyway:** Uso de migrations versionadas para garantir a integridade do schema do banco de dados em qualquer ambiente de execução.

---
## ☁️ Cloud-Ready Development

O **MatchCarreira** está totalmente preparado para o desenvolvimento em nuvem. Graças ao **GitHub Codespaces** e **DevContainers**, você pode rodar todo o ecossistema (API + PostgreSQL + Redis) diretamente no seu navegador, eliminando a "desordem técnica" de configuração local.

<div align="center">

[![Open in GitHub Codespaces](https://img.shields.io/static/v1?style=for-the-badge&label=GitHub+Codespaces&message=Abrir+Projeto&color=24292e&logo=github)](https://github.com/codespaces/new?hide_repo_select=true&ref=main&repo=KelvynAmaral/match-carreira-backend)

</div>


## 📖 Documentação da API

Com a aplicação em execução via **Docker**, a interface interativa fica disponível para testes imediatos:

[ ![Swagger](https://img.shields.io/badge/Acessar_Swagger_UI-85EA2D?style=for-the-badge&logo=swagger&logoColor=black) ](http://localhost:8080/swagger-ui.html)