# Gestão de Clínicas API

API backend para gerenciamento de clínicas médicas, desenvolvida com Java e Spring Boot.  
O projeto foi criado com foco em praticar modelagem de entidades, relacionamentos com JPA, validação de dados e construção de endpoints REST.

## Status do projeto

🚧 Em desenvolvimento

### Funcionalidades implementadas
- Cadastro de pacientes
- Listagem de pacientes
- Busca de paciente por CPF
- Cadastro de médicos
- Listagem de médicos por especialidade
- Agendamento de consultas
- Cancelamento de consultas
- Listagem de consultas por período
- Listagem de consultas agendadas de um paciente
- Uso de DTOs para entrada e saída de dados
- Validação com `@Valid`

### Próximas funcionalidades
- Registro de pagamentos
- Listagem de pagamentos pendentes
- Relatórios com queries SQL/JPA
- Tratamento global de exceções
- Paginação
- Spring Security
- Docker

## Tecnologias utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Maven
- Bean Validation

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
- Uma consulta possui um pagamento

## Boas práticas aplicadas

- Uso de DTOs para não expor entidades diretamente
- Validação de dados com `@Valid`
- Organização em camadas
- Mapeamento de relacionamentos com JPA

## Como executar o projeto

### Pré-requisitos
- Java 17+
- Maven
- PostgreSQL

### Passos

1. Clone o repositório:
```bash
git clone <https://github.com/kellyson04/gestao-de-clinicas-api>
