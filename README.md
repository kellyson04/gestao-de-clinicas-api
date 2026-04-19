# Gestão de Clínicas API

API REST para gerenciamento de clínicas médicas, desenvolvida com Java e Spring Boot.

O projeto foi criado com foco em praticar modelagem de entidades, relacionamentos com JPA, validação de dados, regras de negócio e construção de endpoints REST, seguindo uma arquitetura em camadas.

## Status do projeto

🚧 Em desenvolvimento

## Funcionalidades implementadas

- Cadastro de pacientes
- Listagem de pacientes
- Busca de paciente por CPF
- Cadastro de médicos
- Listagem de médicos por especialidade
- Agendamento de consultas
- Cancelamento de consultas
- Listagem de consultas por período
- Listagem de consultas agendadas de um paciente
- Registro de pagamentos
- Listagem de pagamentos pendentes
- Uso de DTOs para entrada e saída de dados
- Validação com `@Valid`
- Tratamento global de exceções

## Próximas melhorias

- Paginação nas listagens
- Spring Security com autenticação e autorização
- Docker e Docker Compose
- Testes unitários e de integração
- Documentação com Swagger/OpenAPI
- Relatórios financeiros e operacionais
- Melhorias nas queries de consulta por período

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

## Entidades principais

### Patient
- id
- name
- cpf
- birthDate

### Doctor
- id
- name
- specialty

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
- Uma consulta possui no máximo um pagamento

## Regras de negócio

- Pacientes são identificados por CPF único
- Consultas são criadas com status `SCHEDULED`
- Consultas canceladas recebem status `CANCELED`
- Não é possível cancelar uma consulta já cancelada ou já realizada
- Não é possível processar pagamento para consulta cancelada ou já realizada
- Cada consulta possui no máximo um pagamento
- Pagamentos podem ter status `PENDING`, `PAID` ou `CANCELED`

## Variáveis de ambiente

Antes de executar o projeto, configure as seguintes variáveis:

| Variável | Descrição | Exemplo |
|---|---|---|
| `DB_URL` | URL de conexão com PostgreSQL | `jdbc:postgresql://localhost:5432/gestao_clinicas` |
| `DB_USERNAME` | Usuário do banco | `postgres` |
| `DB_PASSWORD` | Senha do banco | `postgres` |

## Como executar o projeto

### Pré-requisitos

- Java 21+
- PostgreSQL
- Maven ou Maven Wrapper

### Passos

1. Clone o repositório:

```bash
git clone https://github.com/kellyson04/gestao-de-clinicas-api.git
```

2. Acesse a pasta do projeto:

```bash
cd gestao-de-clinicas-api
```

3. Configure as variáveis de ambiente:

```bash
DB_URL=jdbc:postgresql://localhost:5432/gestao_clinicas
DB_USERNAME=postgres
DB_PASSWORD=postgres
```

4. Execute o projeto:

```bash
./mvnw spring-boot:run
```

No Windows:

```bash
mvnw.cmd spring-boot:run
```

5. A API estará disponível em:

```bash
http://localhost:8081
```

## Endpoints principais

### Pacientes

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/clinica/patients` | Cadastra um paciente |
| `GET` | `/api/clinica/patients` | Lista todos os pacientes |
| `GET` | `/api/clinica/patients/{cpf}` | Busca paciente por CPF |

### Médicos

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/clinica/doctors` | Cadastra um médico |
| `GET` | `/api/clinica/doctors?specialty=CARDIOLOGIA` | Lista médicos por especialidade |

### Consultas

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/clinica/appointments` | Agenda uma consulta |
| `PATCH` | `/api/clinica/appointments/{appointmentId}` | Cancela uma consulta |
| `GET` | `/api/clinica/appointments?firstDate=2026-04-01T00:00:00&lastDate=2026-04-30T23:59:59` | Lista consultas por período |
| `GET` | `/api/clinica/appointments/{patientId}` | Lista consultas agendadas de um paciente |

### Pagamentos

| Método | Endpoint | Descrição |
|---|---|---|
| `POST` | `/api/clinica/payments` | Processa pagamento de uma consulta |
| `GET` | `/api/clinica/payments` | Lista pagamentos pendentes |

## Exemplos de JSON

### Cadastro de paciente

```json
{
  "name": "Maria Silva",
  "cpf": "12345678901",
  "birthDate": "1995-08-20"
}
```

### Cadastro de médico

```json
{
  "name": "Dr. João Pereira",
  "specialty": "CARDIOLOGIA"
}
```

### Agendamento de consulta

```json
{
  "patientId": 1,
  "doctorId": 1,
  "dateTime": "2026-04-20T14:30:00"
}
```

### Registro de pagamento

```json
{
  "appointmentId": 1,
  "amount": 250.00
}
```

## Especialidades disponíveis

- `CARDIOLOGIA`
- `COLOPROCTOLOGIA`
- `DERMATOLOGIA`
- `GASTROENTEROLOGIA`
- `GERIATRIA`
- `HOMEOPATIA`
- `INFECTOLOGIA`
- `MASTOLOGIA`
- `NEFROLOGIA`
- `NEUROCIRURGIA`
- `NEUROLOGIA`
- `NUTROLOGIA`
- `PEDIATRIA`
- `PNEUMOLOGIA`
- `PSIQUIATRIA`
- `RADIOTERAPIA`
- `REUMATOLOGIA`
- `UROLOGIA`

## Boas práticas aplicadas

- Uso de DTOs para não expor entidades diretamente
- Validação de dados com `@Valid`
- Organização em camadas
- Mapeamento de relacionamentos com JPA
- Tratamento centralizado de exceções

## Estrutura do projeto

```txt
src/main/java
└── com/kellyson/gestaodeclinicasapi/gestao_de_clinicas_api
    ├── controller
    ├── dto
    ├── entity
    ├── enums
    ├── exception
    ├── mapper
    ├── repository
    └── service
```

## Autor

Desenvolvido por Kellyson Lopes como projeto prático de estudo em backend com Java e Spring Boot.
