# desafio-totvs

# Projeto de Autenticação e Autorização com Spring Boot

## Descrição

Este projeto é uma aplicação Java 21 desenvolvida com Spring Boot e Spring Security para autenticação e autorização. O projeto é organizado com base na arquitetura DDD (Domain-Driven Design) e utiliza PostgreSQL como banco de dados.

## Tecnologias Utilizadas

- **Java**: 21
- **Spring Boot**: Framework principal para desenvolvimento da aplicação.
- **Spring Security**: Gerencia a autenticação e autorização.
- **PostgreSQL**: Banco de dados utilizado para armazenar dados da aplicação.
- **Docker**: Containeriza a aplicação e o banco de dados para facilitar a execução e a manutenção.

## Usuário para Teste

Para testar a aplicação, você pode usar o seguinte usuário:

- **Usuário**: `user`
- **Senha**: `password`

## Documentação

A aplicação está documentada com Swagger. Para acessar a documentação da API, conecte-se ao seguinte URL:

- **Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Estrutura do Projeto

O projeto está organizado de acordo com a arquitetura DDD (Domain-Driven Design). A estrutura do projeto é modular e facilita a manutenção e a escalabilidade da aplicação.

## Containerização com Docker

O projeto está containerizado usando Docker. Para executar a aplicação e o banco de dados, siga os passos abaixo:

1. **Certifique-se de que o Docker e o Docker Compose estão instalados** na sua máquina.

2. **Navegue até o diretório raiz do projeto** onde está localizado o arquivo `docker-compose.yml`.

3. **Execute o comando abaixo para construir e iniciar os containers**:

    ```bash
    docker-compose up --build
    ```

   Isso iniciará a aplicação e o banco de dados PostgreSQL em containers Docker. A aplicação estará disponível na porta configurada no `docker-compose.yml` (por padrão, a porta 8080).

## Endpoints Protegidos

Os endpoints da aplicação são protegidos e requerem autenticação. A página de login está disponível em `/login`.

## Testes Unitários 
Cobertura de 90% das classes

## Contribuições

Contribuições são bem-vindas! Se você encontrar algum problema ou tiver sugestões para melhorias, sinta-se à vontade para abrir uma issue ou um pull request.
