# TechStock - Controle de Estoque (Backend)

API REST para gerenciamento de estoque de componentes eletrônicos e periféricos de TI.

## 🚀 Tecnologias

- Spring Boot 3.3+
- Java 21
- Maven
- H2 Database (desenvolvimento)
- Jakarta JPA/Hibernate

## 🚀 Instalação e Execução

### Pré-requisitos
- Java 21+
- Maven 3.8+

### Passos

1. **Compilar o projeto**
   ```bash
   mvn clean install
   ```

2. **Executar a aplicação**
   ```bash
   mvn spring-boot:run
   ```

3. A API estará disponível em `http://localhost:8080`

## 📚 Endpoints

### Mercadorias
- `GET /api/mercadorias` - Listar todas as mercadorias (com estoque > 0)
- `POST /api/mercadorias` - Criar nova mercadoria
- `GET /api/mercadorias/{id}` - Buscar mercadoria por ID
- `PUT /api/mercadorias/{id}` - Atualizar mercadoria
- `DELETE /api/mercadorias/{id}` - Deletar mercadoria

### Fornecedores
- `GET /api/fornecedores` - Listar todos os fornecedores
- `POST /api/fornecedores` - Criar novo fornecedor
- `GET /api/fornecedores/{id}` - Buscar fornecedor por ID
- `PUT /api/fornecedores/{id}` - Atualizar fornecedor
- `DELETE /api/fornecedores/{id}` - Deletar fornecedor
- `GET /api/fornecedores/{id}/mercadorias` - Listar mercadorias de um fornecedor

## 📋 Modelo de Dados

### Mercadoria
```json
{
  "id": 1,
  "nome": "Placa de Vídeo RTX 4060",
  "descricao": "NVIDIA GeForce RTX 4060...",
  "categoria": "Placa de Vídeo",
  "quantidade": 5,
  "preco": 1299.99,
  "dataValidade": "2025-12-31",
  "dataCadastro": "2025-06-02",
  "fornecedor": { "id": 1, "nome": "Tech Distribuidor" }
}
```

### Fornecedor
```json
{
  "id": 1,
  "nome": "Tech Distribuidor LTDA",
  "telefone": "(45) 3254-5555",
  "email": "contato@techdistribuidor.com.br",
  "endereco": "Rua das Tecnologias, 100",
  "bairro": "Centro",
  "cidade": "Missal",
  "uf": "PR",
  "observacoes": "Fornecedor confiável",
  "ativo": true
}
```

## ⚙️ Configuração

Edite `src/main/resources/application.properties`:

```properties
spring.application.name=controle-estoque-back
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true

app.cors.allowed-origins=http://localhost:4200
```

## 🔐 Validações

- Mercadoria:
  - Nome: obrigatório, não pode estar vazio
  - Quantidade: >= 0
  - Preço: > 0
  - Data de Validade: obrigatória
  - Fornecedor: obrigatório e ativo

- Fornecedor:
  - Nome: obrigatório
  - Telefone: obrigatório
  - Endereço: obrigatório
  - Não é possível excluir fornecedor com mercadorias vinculadas

## 🛠️ Tratamento de Erros

A API retorna respostas estruturadas:

```json
{
  "erro": "Mensagem de erro",
  "timestamp": "2025-06-02T10:30:00Z"
}
```

Códigos HTTP:
- 200: OK
- 201: Created
- 204: No Content
- 400: Bad Request
- 404: Not Found
- 409: Conflict (fornecedor com mercadorias)
- 500: Internal Server Error

## 📦 Estrutura do Projeto

```
src/main/java/com/avaliacao/
├── controller/          # Controladores REST
├── service/            # Lógica de negócio
├── repository/         # Persistência de dados
├── model/              # Entidades JPA
├── dto/                # Data Transfer Objects
├── exception/          # Exceções customizadas
├── config/             # Configurações (CORS, etc)
└── AvaliacaoApplication.java
```

## 💻 Requisitos Atendidos

- [x] CRUD de Mercadorias
- [x] CRUD de Fornecedores
- [x] Campo `dataValidade` em Mercadoria
- [x] Listar mercadorias por fornecedor
- [x] Listar apenas mercadorias com estoque > 0
- [x] Impedir deletar fornecedor com mercadorias
- [x] Validação em DTOs
- [x] Tratamento de erros customizado
- [x] CORS configurado
- [x] Padrão MVC/Arquitetura limpa
