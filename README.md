# Desafio Mini Autorizador

Este é um sistema de autorização de transações para cartões, implementado como uma API REST utilizando Spring Boot. Ele permite a criação de cartões, consulta de saldo e realização de transações.

---

## 📌 Funcionalidades

- **Criar um novo cartão**  
- **Consultar saldo de um cartão**  
- **Realizar transações com um cartão**  
- **Endpoint protegido para autenticação**  

---

## 🚀 Endpoints

### 📌 Cartões

#### ➤ Criar um Cartão

**Método:** `POST /cartoes`  

**Request Body:**
```json
{
  "numeroCartao": "6549873025634501",
  "senha": "1234"
}
```
**Respostas possíveis:**
- ✅ `201 CREATED` - Cartão criado com sucesso.
- ❌ `422 UNPROCESSABLE ENTITY` - Cartão já existe.
- ❌ `500 INTERNAL SERVER ERROR` - Erro inesperado.

---

#### ➤ Consultar Saldo

**Método:** `GET /cartoes/{numeroCartao}`  

**Parâmetro de Path:**  
- `numeroCartao` (string) - Número do cartão a ser consultado.

**Respostas possíveis:**
- ✅ `200 OK` - Retorna o saldo do cartão.
- ❌ `500 INTERNAL SERVER ERROR` - Erro inesperado.

---

### 💳 Transações

#### ➤ Realizar uma Transação

**Método:** `POST /transacoes`  

**Request Body:**
```json
{
  "numeroCartao": "6549873025634501",
  "senha": "1234",
  "valor": 100.00
}
```
**Respostas possíveis:**
- ✅ `200 OK` - Transação realizada com sucesso.
- ❌ `404 NOT FOUND` - Cartão não encontrado.
- ❌ `422 UNPROCESSABLE ENTITY` - Saldo insuficiente.
- ❌ `401 UNAUTHORIZED` - Senha inválida.
- ❌ `500 INTERNAL SERVER ERROR` - Erro inesperado.

---

### 🔐 Endpoint Protegido

#### ➤ Teste de Autenticação

**Método:** `GET /secured/hello`  

> **⚠ Observação:** A autenticação é feita com um usuário fictício.

**Respostas possíveis:**
- ✅ `200 OK` - Retorna uma mensagem de boas-vindas e o usuário autenticado.

---

## ⚡ Concorrência

O sistema é preparado para lidar com múltiplas transações simultaneamente, garantindo integridade nos saldos dos cartões.

---

## 🧪 Testes

Os testes incluem:  
✔ Testes unitários para os serviços e regras de negócio.  
✔ Testes de integração para verificar o comportamento da API.

---

**Este projeto foi desenvolvido para demonstrar habilidades com Spring Boot, REST APIs e tratamento de erros em um cenário de autorização de transações.**
