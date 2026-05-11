"## Microservicios implementados

Todos los microservicios corren dentro de una misma aplicación Spring Boot en el puerto 8080, compartiendo la base de datos maxcinema_db. Los módulos implementados son: Pelicula (/api/v1/peliculas), Genero (/api/v1/generos), Generos relación (/generos-pelicula), Director (/directores), Directores relación (/directores-pelicula), Idioma (/idiomas), Idiomas relación (/idiomas-pelicula), SalasPelicula (/salas-pelicula), Asiento (/api/v1/asientos), TipoAsiento (/api/v1/tipos-asiento), Cliente (/api/v1/clientes), TipoCliente, Entrada (/api/v1/entradas), MetodoPago (/api/v1/metodos-pago), Region, Comuna, Cine, Sala, TipoSala y TiposSala.

## Funcionalidades implementadas

El sistema implementa CRUD completo para todas las entidades del dominio con persistencia real usando JPA + Hibernate (ddl-auto=update). Incluye validaciones con Bean Validation (@Valid, @NotNull, @NotBlank), separación entre DTOs y entidades en todas las capas, manejo centralizado de excepciones con @ControllerAdvice (GlobalExceptionHandler), respuestas HTTP estructuradas con ResponseEntity y códigos de estado correctos, logs estructurados con SLF4J (@Slf4j) en controllers y services, relaciones entre entidades (@OneToMany, @ManyToOne, @ManyToMany), y arquitectura CSR: Controller → Service → Repository.

## Estructura del proyecto

El código fuente se organiza bajo src/main/java/com/maxcinema/maxcinema/ con los paquetes controller (endpoints REST), service (lógica de negocio), repository (acceso a datos con JpaRepository), model (entidades JPA), DTO (objetos de transferencia) y exception (manejo global de errores).

## Requisitos previos

- Java 21
- Maven
- Laragon con MySQL activo
- VS Code o IntelliJ IDEA

## Pasos para ejecutar

Primero crea la base de datos ejecutando CREATE DATABASE maxcinema_db; en tu cliente MySQL. Luego verifica que src/main/resources/application.properties tenga la URL jdbc:mysql://localhost:3306/maxcinema_db, usuario root y contraseña vacía. Las tablas se crean automáticamente al iniciar gracias a ddl-auto=update.

Para ejecutar, corre ./mvnw spring-boot:run desde la raíz del proyecto (en Windows: mvnw.cmd spring-boot:run). Para verificar que funciona, prueba GET http://localhost:8080/api/v1/peliculas en Postman o el navegador." 
