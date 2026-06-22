CREATE TABLE clientes(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR2(100) NOT NULL,
    rut VARCHAR2(9) NOT NULL,
    email VARCHAR2(100) NOT NULL,
    telefono VARCHAR2(15) NOT NULL
);

CREATE TABLE entradas(
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo_entrada VARCHAR2(10) NOT NULL,
    precio FLOAT(9) NOT NULL,
    cantidad INT(1) NOT NULL,
    horario VARCHAR2(20) NOT NULL,
    pelicula_id INT NOT NULL
);

CREATE TABLE metodo_pago(
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo_pago VARCHAR2(50) NOT NULL
);

CREATE TABLE tipo_cliente(
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR2(50) NOT NULL,
    descuento FLOAT(10) NOT NULL
);