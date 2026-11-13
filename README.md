# TechStock | API REST do CRUD de estoque

API REST da WELL-INDUSTRIES para gerenciar fornecedores e mercadorias de um estoque de eletrônicos. O projeto usa Java 21, Spring Boot 3.2.1, Spring Data JPA e PostgreSQL. A [interface Angular](https://github.com/WELL-INDUSTRIES/PROJETO-JAVA-SISTEMA-DE-ESTOQUE-FRONT) consome esses recursos.

O CRUD permite criar, consultar, atualizar e desativar registros. A exclusão é lógica e a API oferece rotas de reativação.

## Executar localmente

Na raiz do repositório, entre na pasta `back`. Crie um banco PostgreSQL de desenvolvimento e configure a conexão. Exemplo para PowerShell:

```powershell
cd back
$env:SPRING_DATASOURCE_URL = "jdbc:postgresql://localhost:5432/estoque"
$env:SPRING_DATASOURCE_USERNAME = "seu_usuario"
$env:SPRING_DATASOURCE_PASSWORD = "sua_senha_local"
mvn spring-boot:run
```

A API atende em http://localhost:8080. Para gerar o JAR, execute `mvn package` na pasta do módulo Maven.

## Recursos

| Operação | Exemplo |
| --- | --- |
| Listar fornecedores ativos com paginação | `GET /fornecedores?page=0&size=10` |
| Cadastrar e editar fornecedor | `POST /fornecedores` e `PUT /fornecedores/{id}` |
| Desativar e reativar fornecedor | `DELETE /fornecedores/{id}` e `PATCH /fornecedores/{id}/reativar` |
| Consultar mercadorias do fornecedor | `GET /fornecedores/{id}/mercadorias` |
| Listar mercadorias | `GET /mercadorias` |
| Ajustar a quantidade de uma mercadoria | `PATCH /mercadorias/{id}/quantidade` |

O código do módulo está dividido em controllers HTTP, serviços com regras de negócio, repositórios JPA e DTOs para entrada de dados. A configuração local está em `back/src/main/resources/application.properties`.
