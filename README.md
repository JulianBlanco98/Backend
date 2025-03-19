# Backend con Spring Boot (3.4.2)

## Tabla de Contenidos

1. [Descripción](#descripción)
2. [Estructura del Proyecto](#estructura-del-proyecto)
3. [Tecnologías Usadas](#tecnologías-usadas)
4. [Ejecución del Proyecto](#ejecución-del-proyecto)

## Descripción

Este proyecto consiste en la implementación del backend para **PokemonTGCP**, una réplica del backend original desarrollado en **MEAN**, ahora migrado a **Spring Boot**. Está conectado a una base de datos relacional **MySQL**.

## Estructura del Proyecto

El proyecto se organiza en dos carpetas principales:

- **Main**: Contiene el código del backend.
- **Test**: Incluye pruebas unitarias y de integración, con el objetivo de alcanzar un **coverage > 80%**.

### Estructura de Directorios en `Main`

- `Config`: Contiene archivos de configuración para rutas públicas y privadas, CORS, autenticación JWT, entre otros.
- `Controller`: Gestiona la comunicación entre el **frontend** y la **capa de servicio**.
- `DTO`: Define las clases de transferencia de datos utilizadas entre el **service** y el **repository**.
- `Exception`: Implementa el manejo global de excepciones.
- `Mapper`: Mapea las clases **model** a **DTO** y viceversa mediante **MapStruct**.
- `Model`: Contiene las entidades que representan las tablas de la base de datos.
- `Repository`: Implementa la interacción con la base de datos mediante **JPA** y consultas personalizadas.
- `Runner`: Configuración previa a la ejecución del servidor.
- `Security`: Gestiona la autenticación y recuperación del email desde el token JWT.
- `Service`: Implementa la lógica de negocio y consultas HTTP.
- `Specification`: Permite la creación de filtros dinámicos para consultas más flexibles.

## Tecnologías Usadas

- **Spring Boot**: Framework base del proyecto, generado con **Spring Initializr**.
- **Lombok**: Reduce el código repetitivo en **entidades** y **DTOs**.
- **Spring Security**: Protege endpoints sensibles mediante autenticación y autorización.
- **JWT (JSON Web Token)**: Implementa autenticación y permite enviar información al frontend de forma segura.
- **MySQL**: Base de datos relacional.
- **JPA (Java Persistence API)**: Realiza operaciones en la base de datos.
- **MapStruct**: Facilita la conversión entre **entidades** y **DTOs**.
- **Specification API**: Permite la creación de filtros dinámicos en las consultas.
- **JUnit 5**: Framework para pruebas unitarias e integración.
- **Mockito**: Simula llamadas HTTP en los controladores para pruebas unitarias.

## Ejecución del Proyecto

### Requisitos Previos

Asegúrate de tener instalados:

- **Java 17 o superior**
- **Maven**
- **MySQL** con la base de datos configurada

### Pasos para ejecutar

Si utilizas un IDE como **IntelliJ IDEA** o **Eclipse**, simplemente ejecuta la clase principal `BackendApplication`.

También puedes ejecutar el proyecto desde la terminal con:

```sh
mvn spring-boot:run
```

Si deseas ejecutar pruebas:

```sh
mvn test
```
