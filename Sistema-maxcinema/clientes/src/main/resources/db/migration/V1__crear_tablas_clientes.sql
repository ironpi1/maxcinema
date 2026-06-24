CREATE TABLE clientes(
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    rut VARCHAR(9) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefono VARCHAR(15) NOT NULL
);

CREATE TABLE entradas(
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo_entrada VARCHAR(10) NOT NULL,
    precio FLOAT(9) NOT NULL,
    cantidad INT(1) NOT NULL,
    horario VARCHAR(20) NOT NULL,
    pelicula_id INT NOT NULL
);

CREATE TABLE metodo_pago(
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo_pago VARCHAR(50) NOT NULL
);

CREATE TABLE tipo_cliente(
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(50) NOT NULL,
    descuento FLOAT(10) NOT NULL
);