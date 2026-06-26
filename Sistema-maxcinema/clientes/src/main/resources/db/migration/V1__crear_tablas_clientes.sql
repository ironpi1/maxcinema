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

INSERT INTO 
    clientes (nombre, rut, email, telefono) 
VALUES 
    ('Juan Pérez', '12345678-9', 'juan.perez@email.com', '+56912345678'),
    ('María González', '98765432-K', 'maria.g@email.com', '+56987654321'),
    ('Pedro Soto', '112233445', 'pedro.soto@email.com', '+56911223344');

INSERT INTO 
    entradas (tipo_entrada, precio, cantidad, horario, pelicula_id)
VALUES 
    ('2D Normal', 5000.0, 2, '15:30', 101),
    ('4DX', 7500.0, 1, '18:00', 102),
    ('Palco Premier', 9500.0, 4, '21:15', 103);

INSERT INTO 
    metodo_pago (tipo_pago) 
VALUES 
    ('Tarjeta de Crédito'),
    ('Tarjeta de Débito'),
    ('Efectivo');

INSERT INTO 
    tipo_cliente (tipo, descuento) 
VALUES 
    ('General', 0.0),
    ('Estudiante', 15.0),
    ('Tercera Edad', 30.0);