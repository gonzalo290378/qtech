# DataTree

This project is a Java Spring Boot REST API for managing a tree structure of nodes with parent-child relationships.

## 🛠️ Technologies

- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database (in-memory)
- Lombok

## 🚀 How to Run

Using Maven: 
```bash
./mvnw spring-boot:run

```

## 🧪 H2 Console
URL: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:datatree-db

Username: sa

Password: (leave empty)

## 🌳 Tree Structure
Each Node entity contains:

id: unique identifier

name: label of the node

parentId: reference to the parent Node

children: list of child nodes

## ✅ Features

Create nodes

Get direct children or all descendants

Delete nodes (with cascade to children)

Move nodes

## 📖 Notes
Uses in-memory H2 database for testing.

Spring Boot automatically creates the schema based on entity mappings.

Cascade and orphan removal are used to manage child node deletions automatically.

## Open API

http://localhost:8080/swagger-ui/index.html