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

</div>

## 📖 Documentação Técnica
Acesse os detalhes da engenharia do projeto nos links abaixo:
* 🏗️ [**Architecture & Data Flow**](./docs/ARCHITECTURE.md)
* 🗄️ [**Data Modeling (ERD)**](./docs/DATABASE.md)
* 🛠️ [**Integration Guide**](./docs/INTEGRATION.md)
* 🆘 [**Help & Troubleshooting**](./docs/HELP.md)

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
