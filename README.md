# MaxCinema

Sistema de gestión de cines desarrollado con arquitectura de microservicios usando Spring Boot.

## Integrantes

- Antonella Aliaga — microservicios peliculas y salas
- Ignacio Fuentes — microservicio clientes
- Rodrigo Pérez — microservicio cines

## Microservicios implementados

- **eureka** (puerto 8761) — servidor de descubrimiento
- **gateway** (puerto 8080) — enrutamiento centralizado
- **peliculas** (puerto dinámico) — gestión de películas, géneros, directores, idiomas y salas asociadas a película
- **salas** (puerto dinámico) — gestión de salas, tipos de sala, asientos y tipos de asiento
- **cines** (puerto dinámico) — gestión de cines, regiones y comunas
- **clientes** (puerto dinámico) — gestión de clientes, tipos de cliente, entradas y métodos de pago

## Rutas principales del Gateway

Todas las peticiones pasan por `http://localhost:8080`.

Microservicio peliculas:
- /api/v1/peliculas
- /api/v1/generos
- /api/v1/generos-pelicula
- /api/v1/directores
- /api/v1/directores-pelicula
- /api/v1/idiomas
- /api/v1/idiomas-pelicula
- /api/v1/salas-pelicula

Microservicio salas:
- /api/v1/salas
- /api/v1/tipo-sala
- /api/v1/tipos-salas
- /api/v1/asientos
- /api/v1/tipo-asiento

Microservicio cines:
- /api/v1/cines
- /api/v1/cine
- /api/v1/region
- /api/v1/comuna

Microservicio clientes:
- /api/v1/clientes
- /api/v1/tipo-cliente
- /api/v1/entradas
- /api/v1/metodo-pago

## Documentación Swagger

La UI de Swagger está disponible de forma centralizada en el gateway:

- Swagger UI unificado: http://localhost:8080/swagger-ui/index.html
- OpenAPI peliculas: http://localhost:8080/peliculas/api-docs
- OpenAPI cines: http://localhost:8080/cines/api-docs
- OpenAPI salas: http://localhost:8080/salas/api-docs
- OpenAPI clientes: http://localhost:8080/clientes/api-docs

## Requisitos previos

- Java 21
- Maven
- MySQL 8 activo (Laragon, XAMPP o instalación directa)

Las bases de datos se crean automáticamente al iniciar cada microservicio.

## Cómo ejecutar localmente

**Opción 1 — script automático (solo Windows)**

Desde la raíz del proyecto ejecutar:

```
iniciar-todo.bat
```

Levanta todos los servicios en el orden correcto y espera a que Eureka esté listo antes de iniciar los demás.

**Opción 2 — manual**

Abrir una terminal por cada microservicio y ejecutar `mvnw.cmd spring-boot:run` (Windows) o `./mvnw spring-boot:run` (Linux/Mac), en este orden:

1. Sistema-maxcinema/eureka
2. Sistema-maxcinema/gateway
3. Sistema-maxcinema/peliculas
4. Sistema-maxcinema/cines
5. Sistema-maxcinema/salas
6. Sistema-maxcinema/clientes

Para verificar que todo está funcionando, el dashboard de Eureka queda en http://localhost:8761 y deben aparecer los 4 microservicios registrados.

## Perfiles de entorno

Cada microservicio tiene configurados tres perfiles: dev, test y prod. Por defecto corre en dev. Para cambiar de perfil:

```
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

## Pruebas unitarias

Cada microservicio tiene sus pruebas en `src/test/java`. Para ejecutarlas:

```
./mvnw test
```
