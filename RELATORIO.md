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

## Estado atual

- projeto configurado para usar PostgreSQL local
- banco esperado: `finance`
- usuario: `postgres`
- senha em uso na configuracao local: `12345`
- porta correta identificada: `5433`
- aplicacao voltou a buildar/subir normalmente

## Observacao tecnica

Para ambiente real, a senha nao deve ficar fixa em arquivo versionado. O proximo ajuste recomendado e trocar por variavel de ambiente ou profile local.
