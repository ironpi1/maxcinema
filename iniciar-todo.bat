
@echo off
 
echo Iniciando Servidor de Descubrimiento Eureka (Puerto 8761)...
cd Sistema-maxcinema\eureka
start cmd /k "mvnw.cmd spring-boot:run"
 
echo Esperando 15 segundos a que Eureka se estabilice...
timeout /t 15 /nobreak > nul
 
echo Iniciando API Gateway...
cd ..\gateway
start cmd /k "mvnw.cmd spring-boot:run"
 
echo Iniciando Microservicio Peliculas...
cd ..\peliculas
start cmd /k "mvnw.cmd spring-boot:run"
 
echo Iniciando Microservicio Cines...
cd ..\cines
start cmd /k "mvnw.cmd spring-boot:run"
 
echo Iniciando Microservicio Salas...
cd ..\salas
start cmd /k "mvnw.cmd spring-boot:run"
 
echo Iniciando Microservicio Clientes...
cd ..\clientes
start cmd /k "mvnw.cmd spring-boot:run"
 
echo.
echo Ecosistema MaxCinema lanzado. Dashboard disponible en http://localhost:8761