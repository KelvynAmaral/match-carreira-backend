
## Infraestrutura e Fluxo de Dados
A aplicação utiliza **Redis** para cache de perfis, reduzindo drasticamente a carga no banco de dados em operações de leitura frequentes.

```mermaid
flowchart TD
    Client[Clientes Web/Mobile] --> API[Spring Boot API]
    API --> Auth[Spring Security + JWT]
    API --> Cache{Redis Cache}
    
    Cache -- Hit --> API
    Cache -- Miss --> Services[Serviços / Regras de Negócio]
    
    %% Conexão com o bloco de infraestrutura
    Services --> Infra
    
    subgraph Infra ["Infraestrutura de Dados e Ambiente"]
        direction LR
        Redis[Redis Server] --- DB[(PostgreSQL)] --- Docker[Docker / Compose]
    end

    %% Estilização
    classDef interface fill:#e1f5fe,stroke:#01579b,stroke-width:2px,color:#01579b;
    classDef logic fill:#fff3e0,stroke:#e65100,stroke-width:2px,color:#e65100;
    classDef security fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px,color:#2e7d32;
    classDef data fill:#ede7f6,stroke:#4527a0,stroke-width:2px,color:#4527a0;
    classDef cache fill:#ffebee,stroke:#c62828,stroke-width:2px,color:#c62828;
    classDef infra_box fill:#f5f5f5,stroke:#424242,stroke-width:2px,stroke-dasharray: 5 5;

    class Client interface;
    class API,Services logic;
    class Auth security;
    class DB data;
    class Cache,Redis cache;
    class Infra infra_box;
    
```

## Contextos Delimitados (Bounded Contexts)
| Contexto | Responsabilidade |
| :--- | :--- |
| 🔐 **auth** | Autenticação JWT e Segurança. |
| 👤 **perfil** | Gestão de Currículos e Skills (Core). |
| ⚙️ **usuário** | Governança de Contas e Onboarding. |
EOF