# Gestão de Clínicas API

API REST para gerenciamento de clínicas médicas, desenvolvida com Java e Spring Boot.

O projeto tem como objetivo praticar backend com foco em modelagem de entidades, relacionamentos com JPA, regras de negócio, validações, paginação, tratamento de exceções e documentação com Swagger/OpenAPI.

## Status do projeto

🚧 Em desenvolvimento

## Funcionalidades

- Cadastro e listagem de pacientes
- Busca de paciente por CPF
- Desativação de pacientes com soft delete
- Cadastro de médicos
- Listagem de médicos por especialidade
- Desativação de médicos com soft delete
- Agendamento e cancelamento de consultas
- Listagem de consultas por período
- Listagem de consultas por paciente ou médico
- Registro, confirmação e nova tentativa de pagamento
- Listagem de pacientes com pagamentos pendentes
- Paginação nas listagens
- Tratamento global de exceções
- Documentação da API com Swagger/OpenAPI

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Flyway
- Bean Validation
- Lombok
- Maven
- Swagger/OpenAPI

## Principais conceitos aplicados

- Arquitetura em camadas
- DTOs para entrada e saída de dados
- Mappers para conversão entre entidade e DTO
- Validação com `@Valid`
- Relacionamentos JPA
- Paginação com `Pageable`
- Transações com `@Transactional`
- Soft delete
- Tratamento centralizado de exceções
- Queries com Spring Data JPA e JPQL

## Entidades principais

### Patient

- id
- name
- cpf
- birthDate
- isActive

### Doctor

- id
- name
- specialty
- isActive

### Appointment

- id
- patient
- doctor
- dateTime
- status

### Payment

- id
- appointment
- amount
- status

## Relacionamentos

- Um paciente pode ter várias consultas
- Um médico pode ter várias consultas
- Uma consulta pode ter pagamento associado
- Um pagamento pertence a uma consulta

## Regras de negócio

- Pacientes são identificados por CPF único
- Pacientes e médicos podem ser desativados sem remoção física do banco
- Não é possível agendar consulta para paciente ou médico desativado
- Consultas são criadas com status `SCHEDULED`
- Consultas canceladas recebem status `CANCELED`
- Não é possível cancelar consulta já cancelada ou já realizada
- Não é possível registrar pagamento para consulta cancelada ou já realizada
- Não é possível registrar novo pagamento se já existir pagamento `PENDING` ou `PAID` para a consulta
- Pagamentos podem ter status `PENDING`, `PAID` ou `CANCELED`

## Como executar

### Pré-requisitos

- Java 21+
- PostgreSQL
- Maven ou Maven Wrapper

### Variáveis de ambiente

| Variável | Descrição | Exemplo |
|---|---|---|
| `DB_URL` | URL de conexão com PostgreSQL | `jdbc:postgresql://localhost:5432/gestao_clinicas` |
| `DB_USERNAME` | Usuário do banco | `postgres` |
| `DB_PASSWORD` | Senha do banco | `postgres` |

### Passos

```bash
git clone https://github.com/kellyson04/gestao-de-clinicas-api.git
cd gestao-de-clinicas-api
