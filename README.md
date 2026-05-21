# Gestão de Clínicas API

API REST para gerenciamento de clínicas médicas, desenvolvida com Java e Spring Boot.

O projeto tem como objetivo praticar backend com foco em modelagem de entidades, relacionamentos com JPA, autenticação com JWT, autorização por perfis de usuário, regras de negócio, validações, paginação, filtros dinâmicos, relatórios, tratamento de exceções e documentação com Swagger/OpenAPI.

## Status do projeto

Em desenvolvimento

## Funcionalidades

- Cadastro e autenticação de usuários
- Login com geração de token JWT
- Controle de acesso por perfil de usuário
- Cadastro de pacientes
- Listagem paginada de pacientes
- Filtros com Specification de pacientes por nome, cidade, estado e data de nascimento
- Busca de paciente por CPF
- Desativação de pacientes com soft delete
- Cadastro de médicos
- Listagem paginada de médicos
- Filtros dinâmicos de médicos por nome, UF do CRM e especialidade
- Desativação de médicos com soft delete
- Agendamento de consultas
- Cancelamento de consultas
- Finalização de consultas
- Listagem de consultas por período
- Listagem de consultas agendadas por paciente
- Histórico de consultas por paciente
- Listagem de consultas agendadas por médico
- Histórico de consultas por médico
- Registro de pagamentos
- Confirmação de pagamentos
- Cancelamento de pagamentos
- Nova tentativa de pagamento cancelado
- Relatórios operacionais e financeiros da clínica
- Paginação nas listagens
- Tratamento global de exceções
- Documentação da API com Swagger/OpenAPI

## Tecnologias utilizadas

- Java 21
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA
- Hibernate
- PostgreSQL
- Flyway
- Bean Validation
- Lombok
- Maven
- JWT
- Swagger/OpenAPI

## Principais conceitos aplicados

- Arquitetura em camadas
- Controllers, Services, Repositories e Mappers
- DTOs para entrada e saída de dados
- Validação com `@Valid`
- Autenticação stateless com JWT
- Autorização baseada em roles
- Relacionamentos JPA
- Paginação com `Pageable`
- Filtros dinâmicos com Specification
- Transações com `@Transactional`
- Soft delete
- Tratamento centralizado de exceções
- Queries com Spring Data JPA e JPQL
- Projeções com DTOs para relatórios
- Versionamento de banco com Flyway


## Documentação da API

<img width="760" height="902" alt="image" src="https://github.com/user-attachments/assets/cd641033-4dc9-475c-9f78-cad0316b9b09" />

- A documentação interativa está disponível via Swagger/OpenAPI.

**Após iniciar o projeto, acesse:**

```text
http://localhost:8081/swagger-ui/index.html 
```

