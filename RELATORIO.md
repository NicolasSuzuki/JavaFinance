# Relatorio de setup inicial

## Objetivo

Subir o projeto Spring Boot `finance` localmente com PostgreSQL.

## Uso de IA no projeto

A IA esta sendo usada como suporte tecnico para aprendizado e debugging.

Escopo de uso:

- explicar erros encontrados durante a execucao
- ajudar no diagnostico de configuracoes incorretas
- orientar validacoes no terminal e no banco de dados
- apoiar o entendimento do comportamento do Spring Boot no startup

Nao e um uso focado em gerar codigo automaticamente. O objetivo e usar a IA como ferramenta de estudo, investigacao e resolucao de problemas.

Nenhum codigo de regra de negocio, controller, service, entity, repository ou implementacao funcional foi gerado diretamente pela IA para dentro da aplicacao. A IA foi usada para explicar caminhos, revisar configuracoes, diagnosticar erros e orientar os proximos passos tecnicos.

## O que foi identificado

O projeto foi gerado com dependencias de banco e migracao:

- `spring-boot-starter-data-jpa`
- `flyway-core`
- `flyway-database-postgresql`
- `postgresql`

Por causa disso, a aplicacao precisava de um `DataSource` valido para iniciar.

## Erro inicial

Ao executar:

```bash
./mvnw spring-boot:run
```

A aplicacao falhava com erro de configuracao de banco:

```text
Failed to configure a DataSource: 'url' attribute is not specified
Reason: Failed to determine a suitable driver class
```

## Acao tomada

Foi adicionada configuracao de conexao no arquivo [application.properties](/home/suzuki/java-backend/finance/src/main/resources/application.properties:1):

- `spring.datasource.url`
- `spring.datasource.username`
- `spring.datasource.password`
- `spring.datasource.driver-class-name`

Tambem foram mantidas as configuracoes:

- `spring.jpa.hibernate.ddl-auto=validate`
- `spring.flyway.enabled=true`

## Segundo erro encontrado

Depois da configuracao inicial, a aplicacao passou a falhar com:

```text
password authentication failed for user "postgres"
```

## Investigacao

Foi alterada a senha do usuario `postgres` no PostgreSQL:

```sql
ALTER USER postgres WITH PASSWORD '12345';
```

Mesmo assim, a autenticacao TCP continuava falhando. O diagnostico correto veio com:

```bash
sudo -u postgres psql -c "\conninfo"
```

Resultado relevante:

```text
You are connected to database "postgres" as user "postgres" via socket in "/var/run/postgresql" at port "5433".
```

## Causa raiz

A aplicacao estava apontando para `localhost:5432`, mas a instancia correta do PostgreSQL no ambiente estava rodando na porta `5433`.

Isso fazia parecer que a senha estava errada, quando na pratica o destino da conexao estava incorreto.

## Correcao final

A URL do datasource foi ajustada para:

```text
jdbc:postgresql://localhost:5433/finance
```

## Evolucao da task inicial

Apos o setup inicial, a estrutura do projeto foi comparada com a task tecnica proposta:

- criar projeto via Spring Initializr
- adicionar dependencias principais
- adicionar suporte a JWT
- configurar arquivo YAML
- separar environments
- organizar pacotes iniciais
- validar build local

O projeto ja possuia as dependencias principais:

- `spring-boot-starter-web`
- `spring-boot-starter-security`
- `spring-boot-starter-data-jpa`
- `spring-boot-starter-validation`
- `postgresql`
- `lombok`
- `flyway-core`
- `flyway-database-postgresql`

Depois, foi orientada a inclusao das dependencias JWT no `pom.xml`:

- `jjwt-api`
- `jjwt-impl`
- `jjwt-jackson`

Tambem foi orientada a migracao de configuracao de `application.properties` para YAML.

## Separacao de environments

A configuracao foi organizada em arquivos por ambiente:

- `application.yml`
- `application-dev.yml`
- `application-test.yml`
- `application-prod.yml`

Essa separacao permite manter uma configuracao base e ajustar banco, exibicao de SQL e credenciais conforme o ambiente.

No ambiente de desenvolvimento, o projeto continua apontando para o PostgreSQL local na porta `5433`.

No ambiente de producao, a orientacao foi usar variaveis de ambiente para evitar senha fixa no arquivo de configuracao.

## Estrutura de pacotes

A estrutura inicial foi criada dentro do pacote base `com.backend.finance`, mantendo compatibilidade com o scan automatico do Spring Boot:

```text
com.backend.finance
├── auth
├── config
├── common
├── security
├── user
├── category
└── transaction
```

A estrutura esperada pela task citava `com.seuprojeto.finance`, mas o projeto real foi gerado com o pacote base `com.backend.finance`. Tecnicamente isso esta correto, desde que todos os pacotes fiquem abaixo do pacote da classe `FinanceApplication`.

## Uso da IA nesta etapa

Nesta etapa, a IA foi usada para:

- explicar o que significava cada item da task
- indicar onde adicionar dependencias JWT
- explicar a diferenca entre `application.properties` e `application.yml`
- orientar a separacao de profiles `dev`, `test` e `prod`
- explicar por que pastas vazias nao sao versionadas pelo Git
- revisar se os criterios de aceite estavam atendidos

A implementacao da estrutura e das configuracoes foi conduzida manualmente pelo desenvolvedor. A IA nao gerou automaticamente codigo de aplicacao nem implementou funcionalidades diretamente no backend.

## Uso do GPT como PO, PM e Tech Lead

O GPT tambem esta sendo usado como apoio para organizacao do projeto em papeis proximos aos de PO, PM e Tech Lead.

Como PO, o GPT ajudou a transformar a ideia do produto em funcionalidades e objetivos claros para a API de gerenciamento financeiro.

Como PM, o GPT ajudou a organizar o trabalho em sprints, separando a evolucao do projeto em etapas progressivas e mais faceis de acompanhar.

Como Tech Lead, o GPT ajudou a orientar decisoes tecnicas, como estrutura de pacotes, dependencias, configuracao de ambientes, uso de Flyway, seguranca com JWT e validacao do setup local.

Esse uso foi direcionado para planejamento, organizacao, refinamento tecnico e aprendizado. A implementacao do codigo da aplicacao nao foi delegada para a IA.

## Organizacao das Sprints

O projeto foi organizado em tres sprints principais, com foco em construir uma API backend em Java de forma progressiva, comecando pela base da aplicacao, evoluindo para as funcionalidades financeiras e finalizando com melhorias de qualidade tecnica.

### Sprint 1 - Foundation & Auth

A primeira sprint teve como objetivo criar a estrutura inicial do backend e preparar a base tecnica do projeto. Nessa etapa foram definidas as configuracoes principais da aplicacao, banco de dados, seguranca e autenticacao.

Tarefas principais:

- Configurar o projeto Spring Boot
- Criar a estrutura inicial de pacotes
- Configurar PostgreSQL com Docker
- Implementar migrations com Flyway
- Criar o modulo de usuarios
- Implementar cadastro e login
- Configurar autenticacao com JWT
- Proteger rotas privadas com Spring Security

### Sprint 2 - Transactions & Categories

A segunda sprint teve como objetivo implementar o nucleo financeiro da aplicacao, permitindo que o usuario organize suas receitas, despesas e categorias.

Tarefas principais:

- Criar o CRUD de categorias
- Criar o CRUD de transacoes
- Relacionar categorias e transacoes ao usuario autenticado
- Implementar tipos de transacao, como receita e despesa
- Adicionar filtros por periodo, categoria e tipo
- Implementar paginacao nas listagens
- Garantir que cada usuario acesse apenas seus proprios dados

### Sprint 3 - Qualidade, Arquitetura & Testes

A terceira sprint teve como foco melhorar a qualidade tecnica do backend, aproximando o projeto de um padrao mais profissional e sustentavel.

Tarefas principais:

- Implementar testes unitarios
- Criar testes para services e regras de negocio
- Padronizar o tratamento de erros
- Melhorar a organizacao da arquitetura
- Adicionar documentacao da API com Swagger/OpenAPI
- Melhorar logs e respostas da aplicacao
- Garantir um codigo mais desacoplado e facil de manter

## Estado atual

- projeto configurado para usar PostgreSQL local
- banco esperado: `finance`
- usuario: `postgres`
- senha em uso na configuracao local: `12345`
- porta correta identificada: `5433`
- dependencias JWT adicionadas ao `pom.xml`
- configuracao migrada para YAML
- environments separados em arquivos dedicados
- aplicacao voltou a buildar/subir normalmente

## Observacao tecnica

Para ambiente real, a senha nao deve ficar fixa em arquivo versionado. O ajuste recomendado e manter credenciais sensiveis em variaveis de ambiente ou arquivos locais ignorados pelo Git.
