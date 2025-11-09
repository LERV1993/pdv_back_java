
# 🧩 Backend Java - Programación de Vanguardia

![Java](https://img.shields.io/badge/Java-17-orange?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-brightgreen?logo=springboot)
![License](https://img.shields.io/badge/License-MIT-blue)
![Swagger](https://img.shields.io/badge/Docs-Swagger_UI-yellow?logo=swagger)
![Build](https://img.shields.io/badge/Build-Maven-red?logo=apache-maven)

Backend desarrollado en **Java 17** con **Spring Boot**, diseñado para conectarse a una base de datos **MySQL** y exponer su información a través de una **API REST** completamente documentada con **Swagger**.

---

## 🚀 Características principales

- 🧠 Desarrollado con **Java 17** y **Spring Boot 3**
- 💾 Conexión a base de datos **MySQL** mediante **JPA/Hibernate**
- ⚙️ Configuración sencilla mediante archivo `.env`
- 🧩 Estructura modular y escalable
---

## ⚙️ Configuración del entorno

El proyecto utiliza RabbitMQ en un contenedor de docker por lo que es necesario tener docker instalado

En la raíz del proyecto se debe crear un archivo `.env` con la siguiente configuración:

```env
PROFILE=dev
URL=jdbc:mysql://localhost:3306/table_example
USERNAME=root
PASSWORD=
DDL=update
```

- 🧰 Compatible con cualquier base de datos SQL

Para el ejemplo se utiliza la base de datos por defecto que crea [Xampp/Lampp](https://www.apachefriends.org/es/index.html) pero se puedo usar cualquier base SQL.

Para acceder al panel de administración de RabittMQ se debe ingresar a [RabbitMQ](http://localhost:15672) usaurio: admin - Contraseña: admin

## ⬇️ Installation 

```bash
git clone https://github.com/LERV1993/pdv_back_java.git

cd pdv_back_java
```

*Completar archivo .env con los parámetros datos detallados anteriormente.*

Correr proyecto:
```bash
mvn clean package && mvn spring-boot:run
```
---

Levantar RabbitMQ:
```bash
docker compose up
```
---

## 🎯 Endpoints 

- 📚 API REST **totalmente documentada** con **Swagger UI**

```bash
http://localhost:8080/swagger-ui/index.html
```


## 🖋️ Authors 

- [@Andres-ann](https://github.com/Andres-ann)
- [@LERV1993](https://github.com/LERV1993)
- [@omarlosinno](https://github.com/omarlosinno)
- [@RauloGti](https://github.com/RauloGti)
- [@ynunezgon](https://github.com/ynunezgon)