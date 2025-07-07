# 💸 EBANX Take-Home Challenge - Java API

Este é um projeto desenvolvido como parte do processo seletivo para Engenheiro de Software no EBANX.  
A API foi implementada em Java utilizando Spring Boot, com foco em simplicidade, clareza e separação de responsabilidades.

---

## 🚀 Funcionalidades da API

A API possui três endpoints principais:

- **POST `/reset`**  
  Reinicia o estado da aplicação, limpando todas as contas.

- **GET `/balance?account_id=100`**  
  Retorna o saldo da conta informada.  
  Retorna `404 0` se a conta não existir.

- **POST `/event`**  
  Realiza operações com base no tipo:
  
  - `deposit`: adiciona saldo à conta (cria se não existir)
  - `withdraw`: retira saldo (erro se saldo insuficiente ou conta inexistente)
  - `transfer`: transfere saldo de uma conta para outra

---

## 📦 Tecnologias utilizadas

- Java 17
- Spring Boot 
- Maven
- Lombok

---

## 🏗 Estrutura do projeto

