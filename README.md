# 📝 Sistema de Gestão de Tarefas

Este projeto é uma aplicação simples de **gestão de tarefas** desenvolvida com **Jakarta EE (JSF + CDI + JPA/Hibernate)**.  
Ele permite cadastrar, listar, filtrar, editar, concluir e remover tarefas de forma prática através de uma interface web.

---

## 📌 Funcionalidades Implementadas

- (a) **Cadastro de tarefas** com título, descrição, responsável, prioridade, deadline e status.  
- (b) **Edição de tarefas** já cadastradas.  
- (c) **Concluir tarefa** (alterar status para *CONCLUIDA*).  
- (d) **Remoção de tarefas**.  
- (e) **Listagem de tarefas** em uma tabela dinâmica.  
- (f) **Filtros de busca** por número, título/descrição, responsável e status.  
- (g) **Limpar filtros** para restaurar a listagem.  
- (h) **Validações** no formulário (campos obrigatórios).  

---

## ⚙️ Tecnologias Utilizadas

- **Java 17+**
- **Jakarta EE 10** (JSF, CDI)
- **JPA / Hibernate**
- **Maven**
- **PostgreSQL**
- **Apache Tomcat 10+**

---

## 🔧 Configuração do Banco de Dados

1. Crie o banco de dados no PostgreSQL:
```sql
CREATE DATABASE sistema_tarefas;

CREATE USER tarefas_user WITH PASSWORD '1234';
GRANT ALL PRIVILEGES ON DATABASE sistema_tarefas TO tarefas_user;

O arquivo src/main/resources/META-INF/persistence.xml já está configurado:
<property name="jakarta.persistence.jdbc.driver" value="org.postgresql.Driver"/>
<property name="jakarta.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/sistema_tarefas"/>
<property name="jakarta.persistence.jdbc.user" value="tarefas_user"/>
<property name="jakarta.persistence.jdbc.password" value="1234"/>

<property name="hibernate.dialect" value="org.hibernate.dialect.PostgreSQLDialect"/>
<property name="hibernate.hbm2ddl.auto" value="update"/>
<property name="hibernate.show_sql" value="true"/>
<property name="hibernate.format_sql" value="true"/>

🚀 Como executar o projeto localmente:
1.Clonar o repositório:
git clone https://github.com/seu-usuario/sistema-gestao-tarefas.git
cd sistema-gestao-tarefas

2. Compilar o projeto:
mvn clean install

3. Deploy no Tomcat
Copie o .war gerado em target/ para a pasta webapps/ do Tomcat
ou configure o deploy pela sua IDE (IntelliJ/Eclipse).

4.Inicie o Tomcat.

5.Abra no navegador:
http://localhost:8080/sistema-gestao-tarefas

📄 Orientações Rápidas

Banco de dados: PostgreSQL rodando localmente

Usuário padrão: tarefas_user

Senha padrão: 1234

Banco padrão: sistema_tarefas

Porta padrão: 5432

URL da aplicação: http://localhost:8080/sistema-gestao-tarefas
