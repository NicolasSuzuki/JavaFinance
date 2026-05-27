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

Durante o desenvolvimento do projeto, utilizei agentes de Inteligencia Artificial simulando diferentes papeis dentro de uma equipe de produto e tecnologia: Product Manager, Product Owner e Tech Lead.

O agente de Product Manager auxiliou na definicao da visao do produto, escopo inicial, objetivos e evolucao do projeto. O agente de Product Owner apoiou na organizacao do backlog, criacao das sprints, detalhamento das tarefas e definicao dos criterios de aceite. Ja o agente de Tech Lead contribuiu com orientacoes tecnicas, sugestoes de arquitetura, boas praticas backend e organizacao da estrutura da aplicacao.

Esses agentes foram utilizados como apoio ao planejamento e a tomada de decisao, enquanto o desenvolvimento e a implementacao do projeto foram realizados manualmente.

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

Status atual: em andamento.

## Acompanhamento por tasks

### Task 1 - Estrutura inicial do backend

Status: concluida.

Objetivo:

Criar a estrutura inicial do backend.

Tasks tecnicas:

- Criar projeto via Spring Initializr
- Adicionar dependencias principais do Spring Boot
- Adicionar Spring Web
- Adicionar Spring Security
- Adicionar Spring Data JPA
- Adicionar PostgreSQL Driver
- Adicionar Validation
- Adicionar Lombok
- Adicionar Flyway
- Adicionar dependencias JWT
- Configurar arquivos YAML
- Separar environments
- Criar estrutura inicial de pacotes

Criterios de aceite:

- Projeto sobe localmente
- Build sem erros
- Estrutura organizada

Resultado:

- Projeto Spring Boot criado
- Dependencias principais configuradas no `pom.xml`
- Dependencias JWT adicionadas
- Configuracao migrada para YAML
- Ambientes separados em `application.yml`, `application-dev.yml`, `application-test.yml` e `application-prod.yml`
- Estrutura inicial criada abaixo de `com.backend.finance`
- Aplicacao validada localmente com build/teste sem erro

Observacao:

A estrutura esperada pela task usava o pacote de exemplo `com.seuprojeto.finance`. No projeto real, o pacote base e `com.backend.finance`, o que esta correto tecnicamente porque todos os pacotes ficam abaixo da classe principal `FinanceApplication`.

### Task 2 - Banco local com Docker

Status: concluida.

Objetivo:

Subir banco local utilizando Docker.

Tasks tecnicas:

- Criar `docker-compose.yml`
- Configurar PostgreSQL
- Configurar variaveis de ambiente
- Validar conexao com aplicacao

Criterios de aceite:

- Container sobe corretamente
- Banco acessivel
- Aplicacao conecta ao banco
- Persistencia funcionando

Plano de validacao:

- subir o container PostgreSQL com Docker Compose
- confirmar que o container esta em execucao
- testar conexao com `psql`
- ajustar `application-dev.yml` para apontar para o banco do container
- executar a aplicacao ou os testes para validar conexao
- criar ou validar uma migration Flyway para confirmar persistencia/versionamento

Progresso realizado:

- Criado arquivo `docker-compose.yml`
- Configurado servico PostgreSQL com imagem `postgres:16`
- Configurado container `finance-postgres`
- Configurado volume Docker `postgres_data` para persistencia dos dados
- Configurado healthcheck com `pg_isready`
- Criado arquivo `.env.example` com variaveis de referencia
- Ajustado `application-dev.yml` para usar variaveis de ambiente com fallback

Configuracao definida:

- banco: `finance`
- usuario: `postgres`
- senha local: `12345`
- porta interna do container: `5432`
- porta publicada na maquina: `5434`
- URL JDBC em desenvolvimento: `jdbc:postgresql://localhost:5434/finance`

Observacao sobre porta:

Inicialmente foi tentado publicar o PostgreSQL Docker na porta `5432`, mas essa porta ja estava em uso na maquina. Para evitar conflito com outro servico local, a porta publicada do container foi alterada para `5434`.

Comandos orientados para validacao manual:

```bash
docker compose up -d
docker compose ps
docker compose exec postgres pg_isready -U postgres -d finance
psql -h localhost -p 5434 -U postgres -d finance
./mvnw test
```

Status dos criterios de aceite:

- Container sobe corretamente: validado
- Banco acessivel: validado
- Aplicacao conecta ao banco: validado
- Persistencia funcionando: validado em conjunto com a Task 3 de migrations

### Task 3 - Controle de migrations do banco

Status: concluida.

Objetivo:

Controlar migrations do banco.

Tasks tecnicas:

- Configurar Flyway
- Criar migration inicial
- Criar tabela `users`
- Validar execucao automatica

Criterios de aceite:

- Banco atualizado automaticamente
- Historico de migrations funcionando
- Estrutura versionada

Planejamento da modelagem:

Durante o planejamento inicial do projeto Backend Finance, foi realizada a definicao da estrutura principal do banco de dados e da arquitetura base da aplicacao, visando criar uma API REST organizada, escalavel e proxima de um ambiente profissional real.

Nesta etapa, foi definida a separacao das principais entidades do sistema, considerando as regras de negocio necessarias para um sistema de gerenciamento financeiro pessoal. A modelagem inicial do banco foi construida utilizando as tabelas `users`, `categories` e `transactions`, permitindo que cada usuario possua suas proprias categorias e movimentacoes financeiras de forma isolada e segura.

Tambem foi definido que receitas e despesas seriam representadas por uma unica entidade de transacao, diferenciadas atraves de um tipo de movimentacao (`INCOME` e `EXPENSE`). Essa abordagem reduz duplicacao de codigo, simplifica consultas e melhora a escalabilidade futura da aplicacao.

Alem disso, foi utilizado apoio de Inteligencia Artificial durante a etapa de planejamento e modelagem, auxiliando na validacao da estrutura do banco de dados, definicao de relacionamentos entre entidades e identificacao de boas praticas arquiteturais. O uso da IA contribuiu para evitar omissoes importantes na modelagem inicial e para aproximar o projeto de padroes utilizados em aplicacoes backend profissionais.

A estrutura inicial definida permitira futuras evolucoes do sistema, como dashboards financeiros, relatorios mensais, metas financeiras e integracoes com aplicacoes frontend ou mobile.

Progresso realizado:

- Flyway ja estava configurado no `pom.xml`
- Flyway ja estava habilitado nos arquivos de configuracao
- Pasta `src/main/resources/db/migration` definida como local padrao das migrations
- Modelagem inicial planejada com as entidades `users`, `categories` e `transactions`
- Criada migration inicial `V1__create_initials_tables.sql`
- Criada tabela `users`
- Criada tabela `categories`
- Criada tabela `transactions`
- Criados relacionamentos entre usuarios, categorias e transacoes
- Criadas restricoes para tipos `INCOME` e `EXPENSE`
- Validada execucao automatica da migration pelo Flyway
- Validado historico de migrations com `flyway_schema_history`

Resultado:

- Banco atualizado automaticamente ao subir a aplicacao
- Historico de migrations funcionando
- Estrutura inicial do banco versionada em arquivo SQL
- Persistencia inicial validada com PostgreSQL via Docker

Status dos criterios de aceite:

- Banco atualizado automaticamente: validado
- Historico de migrations funcionando: validado
- Estrutura versionada: validado

### Task 4 - Dominio de usuario

Status: concluida.

Objetivo:

Implementar o dominio de usuario na aplicacao.

Tasks tecnicas:

- Criar entidade `User`
- Mapear entidade para a tabela `users`
- Criar repository de usuarios
- Criar DTO de request para criacao de usuario
- Criar DTO de response sem exposicao de senha
- Criar service com regra de criacao de usuario
- Validar email duplicado
- Criar exception especifica para email ja existente
- Criar handler global para resposta de erro
- Criar controller para cadastro de usuario
- Configurar criptografia de senha
- Liberar rota publica de cadastro na camada de seguranca

Progresso realizado:

- Criada entidade `User` em `user/entity`
- Mapeado `id` como `UUID`
- Mapeados campos `name`, `email`, `password`, `createdAt` e `updatedAt`
- Criado `UserRepository` estendendo `JpaRepository<User, UUID>`
- Criados metodos `findByEmail` e `existsByEmail`
- Criado `CreateUserRequest` com validacoes de entrada
- Criado `UserResponse` sem retornar senha
- Criado `UserService` com fluxo de criacao de usuario
- Adicionada validacao para impedir email duplicado
- Criada exception `EmailAlreadyExistsException`
- Criado `GlobalExceptionHandler` para retornar `409 Conflict` em caso de email duplicado
- Criado `UserController` com endpoint `POST /users`
- Criado `SecurityConfig` no pacote `security`
- Configurado `PasswordEncoder` com `BCryptPasswordEncoder`
- Injetado `PasswordEncoder` no `UserService`
- Ajustado fluxo de criacao para salvar `passwordEncoder.encode(request.password())`
- Liberado acesso publico ao endpoint `POST /users`

Validacao realizada:

- O endpoint `POST /users` foi testado manualmente
- O cadastro de usuario com senha valida foi executado com sucesso
- A senha passou a ser salva com hash, nao em texto puro
- A resposta do cadastro nao retorna o campo `password`
- A validacao de senha minima foi testada com senha menor que 6 caracteres
- A aplicacao rejeitou senha invalida conforme a regra definida no DTO

Analise do resultado:

O dominio de usuario foi finalizado com entidade, repository, DTOs, service, controller, validacoes e criptografia de senha. A camada de seguranca foi configurada para permitir acesso publico ao cadastro de usuario, mantendo as demais rotas protegidas.

Durante os testes, senha menor que 6 caracteres foi rejeitada pela validacao definida em `CreateUserRequest`. Esse comportamento confirma a regra de senha minima. Uma melhoria futura possivel e padronizar a resposta de erros de validacao no `GlobalExceptionHandler`, para retornar um corpo de erro mais consistente.

Criterios de aceite sugeridos para a task:

- Entidade `User` criada e mapeada: validado
- Repository criado: validado
- DTOs criados: validado
- Service criado: validado
- Controller criado: validado
- Email duplicado tratado por exception especifica: validado em codigo
- Senha nao exposta na resposta: validado em codigo
- Senha minima de 6 caracteres: validado
- Password hash obrigatorio: validado
- Senha nao salva em texto puro: validado
- Endpoint acessivel publicamente: validado

Proxima acao:

Iniciar a proxima etapa da Sprint 1, relacionada ao fluxo de autenticacao/login e emissao de JWT, ou revisar a padronizacao das respostas de erro antes de avancar para autenticacao.

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

- Sprint atual: Sprint 1 - Foundation & Auth
- Task atual: Task 4 - Dominio de usuario concluida
- projeto configurado para usar PostgreSQL local via Docker em desenvolvimento
- banco esperado: `finance`
- usuario: `postgres`
- senha em uso na configuracao local: `12345`
- porta anterior do PostgreSQL local nativo: `5433`
- porta atual para PostgreSQL via Docker: `5434`
- dependencias JWT adicionadas ao `pom.xml`
- configuracao migrada para YAML
- environments separados em arquivos dedicados
- migration inicial criada e aplicada com Flyway
- tabelas iniciais criadas: `users`, `categories` e `transactions`
- dominio inicial de usuario criado e validado
- endpoint `POST /users` criado
- rota publica `POST /users` liberada na configuracao de seguranca
- senha de usuario criptografada com BCrypt antes de salvar no banco
- validacao de senha minima funcionando
- aplicacao voltou a buildar/subir normalmente

## Observacao tecnica

Para ambiente real, a senha nao deve ficar fixa em arquivo versionado. O ajuste recomendado e manter credenciais sensiveis em variaveis de ambiente ou arquivos locais ignorados pelo Git.
