# Examen Final de Backend - Microservicios con Spring Boot

Este proyecto consiste en la implementación de un sistema distribuido basado en microservicios, desarrollado con Java 17, Spring Boot 3.3.5 y Maven. Está compuesto por tres microservicios:

- ms-auth: Microservicio de autenticación/autorización con JWT
- ms-productos: Gestión de productos
- ms-ordenes: Gestión de órdenes

Cada microservicio es independiente y se comunica con los demás a través de HTTP REST, utilizando Eureka como Service Registry.

## Tecnologías y herramientas utilizadas

- Java 17  
- Spring Boot 3.3.5  
- Spring Cloud Config Server  
- Eureka Server (Service Discovery)  
- HashiCorp Vault (gestión segura de secretos)  
- Spring Security + JWT  
- PostgreSQL  
- JUnit 5 + Mockito (pruebas unitarias)  
- Jacoco (cobertura de código)  
- SonarCloud (análisis de calidad)  
- Postman (pruebas manuales)  
- Patrón de diseño: Builder  
- GitHub (repositorio público)  

## Estructura del Proyecto

backend-examen-final/
├── config-server/  
├── eureka-server/  
├── ms-auth/  
├── ms-productos/  
├── ms-ordenes/  
└── config-repo/   # Repositorio remoto con archivos YAML centralizados

## Configuración

### 1. Configuración centralizada (Spring Cloud Config)
Todos los microservicios cargan su configuración desde un repositorio Git (`config-repo`) con archivos como:

- application.yml  
- ms-auth.yml  
- ms-productos.yml  
- ms-ordenes.yml  

### 2. Eureka Server
El servicio Eureka está configurado en el puerto 8761 y permite que ms-auth, ms-productos y ms-ordenes se registren dinámicamente.

### 3. Vault
Se utiliza HashiCorp Vault para proteger secretos como contraseñas de bases de datos y claves JWT. Se accede vía token estático configurado en cada microservicio.

## Seguridad

- Los endpoints de ms-productos y ms-ordenes están protegidos por tokens JWT.
- Solo usuarios con rol ADMIN pueden acceder a los endpoints.
- Los tokens son generados por ms-auth a través de los endpoints `/auth/register` y `/auth/login`.

## Pruebas unitarias

- Se han desarrollado pruebas para los servicios y controladores de cada microservicio usando JUnit 5 y Mockito.
- La cobertura de código se genera con Jacoco y se sube automáticamente a SonarCloud.

## Análisis de calidad (SonarCloud)

- Se configuró un workflow en GitHub Actions para analizar la calidad del código en cada push.
- Los resultados pueden verse en SonarCloud.

## Pruebas con Postman

- Se realizaron pruebas manuales para validar la funcionalidad de login, creación/listado de productos y órdenes.
- Se validan respuestas HTTP, tokens, y restricciones por rol.

## Patrón de diseño aplicado

- Se utilizó el patrón Builder para la entidad Usuario y otras entidades del sistema.

## Cómo ejecutar el proyecto

1. Clonar el repositorio:

   git clone https://github.com/YahairaLoayza/backend-examen-final.git

2. Levantar servicios en este orden:

   - eureka-server  
   - config-server  
   - vault (ya iniciado localmente con token)  
   - ms-auth  
   - ms-productos  
   - ms-ordenes  

3. Base de datos:

   Asegúrate de tener PostgreSQL corriendo y las siguientes bases de datos creadas:

   - authdb  
   - productosdb  
   - ordenesdb  

## Endpoints

### ms-auth

- POST /auth/register  
- POST /auth/login  
- GET /auth/validate (interno)

### ms-productos (protegido con JWT)

- POST /productos  
- GET /productos  
- PUT /productos/{id}  
- DELETE /productos/{id}

### ms-ordenes (protegido con JWT)

- POST /ordenes  
- GET /ordenes  
- PUT /ordenes/{id}  
- DELETE /ordenes/{id}

## Autora

**Yahaira Loayza**  
Estudiante de Ingeniería de Sistemas e Informática - UTP  
Apasionada por el desarrollo backend y Frontend, microservicios y seguridad de APIs.

## Licencia

Este proyecto es de uso educativo y forma parte del examen final del módulo de Backend con Microservicios.
