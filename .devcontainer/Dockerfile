# Imagem base com Java 21 e Maven pré-instalados
FROM mcr.microsoft.com/devcontainers/java:21-bullseye

# Instalação de utilitários de rede e ferramentas para análise de dados
RUN apt-get update && apt-get install -y \
    curl \
    git \
    procps \
    && apt-get clean