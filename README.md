# Backend Finance

Backend Finance e uma API REST desenvolvida em Java com Spring Boot para gerenciamento financeiro pessoal. O projeto tem como objetivo permitir que usuarios cadastrem suas receitas, despesas e categorias, acompanhem movimentacoes financeiras e consultem informacoes organizadas por periodo.

Além da proposta funcional, este projeto tambem serve como estudo pratico de backend profissional, aplicando conceitos como autenticacao JWT, seguranca com Spring Security, persistencia com JPA, versionamento de banco com Flyway, validacoes, arquitetura em camadas, tratamento de erros e testes automatizados.

## Objetivos do projeto

- Criar uma API backend robusta e bem estruturada
- Praticar desenvolvimento com Java e Spring Boot
- Aplicar autenticacao e autorizacao com JWT
- Gerenciar usuarios, categorias e transacoes financeiras
- Utilizar PostgreSQL como banco de dados
- Versionar alteracoes no banco com Flyway
- Evoluir o projeto com boas praticas de arquitetura, testes e documentacao

## Principais funcionalidades

- Cadastro de usuarios
- Login com geracao de token JWT
- Protecao de rotas privadas
- Cadastro de categorias financeiras
- Cadastro de receitas e despesas
- Listagem de transacoes por usuario
- Filtros por tipo, categoria e periodo
- Paginacao de resultados
- Tratamento padronizado de erros
- Testes unitarios e de integracao

## Tecnologias utilizadas

- Java 21
- Spring Boot 3.5.14
- Spring Web
- Spring Security
- Spring Data JPA
- PostgreSQL
- Flyway
- JWT
- Bean Validation
- Lombok
- Docker
- JUnit
- Mockito
- Swagger / OpenAPI
- Maven Wrapper

## Estrutura inicial

```txt
com.seuprojeto.finance
├── auth
├── config
├── common
├── security
├── user
└── transaction
```

## Uso de IA

Neste projeto, a IA esta sendo utilizada como apoio de estudo e troubleshooting.

O foco de uso e:

- entender erros de configuracao
- investigar falhas de execucao
- aprender conceitos de Spring Boot, PostgreSQL e Maven
- receber explicacoes sobre o fluxo de inicializacao e integracao

O objetivo principal nao e gerar codigo automaticamente, e sim usar a IA para aprender e debugar com mais clareza.

## Pre-requisitos

- JDK 21 instalado
- PostgreSQL instalado
- Banco `finance` criado
- Usuário `postgres` com senha configurada

## Configuracao atual

O projeto esta configurado para conectar no PostgreSQL local usando a porta `5433`.

Arquivo: [application.properties](/home/suzuki/java-backend/finance/src/main/resources/application.properties:1)

```properties
spring.application.name=finance

spring.datasource.url=jdbc:postgresql://localhost:5433/finance
spring.datasource.username=postgres
spring.datasource.password=12345
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true

spring.flyway.enabled=true
```

## Como subir o banco

Verifique se o PostgreSQL esta ativo:

```bash
sudo systemctl status postgresql
```

Se necessario, inicie o servico:

```bash
sudo systemctl start postgresql
```

Crie o banco:

```bash
sudo -u postgres createdb finance
```

Defina a senha do usuario `postgres`:

```bash
sudo -u postgres psql
```

Dentro do `psql`:

```sql
ALTER USER postgres WITH PASSWORD '12345';
\q
```

## Descoberta importante sobre a conexao

Ao validar a conexao local, foi identificado que o PostgreSQL acessado no ambiente estava ouvindo na porta `5433`, nao na `5432`.

Comando usado para confirmar:

```bash
sudo -u postgres psql -c "\conninfo"
```

Saida relevante:

```text
You are connected to database "postgres" as user "postgres" via socket in "/var/run/postgresql" at port "5433".
```

Por isso a URL JDBC precisou ser alterada para:

```text
jdbc:postgresql://localhost:5433/finance
```

## Como testar a conexao com o banco

Teste direto com `psql`:

```bash
psql -h localhost -p 5433 -U postgres -d finance
```

Se a conexao abrir, a aplicacao Spring deve conseguir autenticar tambem.

## Como rodar o projeto

Na pasta do projeto:

```bash
./mvnw spring-boot:run
```

A aplicacao deve subir por padrao na porta `8080`.

## Como rodar os testes

```bash
./mvnw test
```

## Problemas comuns

`Failed to configure a DataSource`

Isso acontece quando as propriedades `spring.datasource.*` nao estao definidas ou apontam para a porta errada.

`password authentication failed for user "postgres"`

Isso normalmente indica uma destas causas:

- senha incorreta no PostgreSQL
- URL apontando para outra instancia
- porta errada

Neste projeto, o problema foi a porta: o banco estava em `5433`.

## Proximos passos sugeridos

- criar a primeira migration Flyway
- adicionar entidades JPA
- criar controllers e services
- mover a senha para variavel de ambiente
