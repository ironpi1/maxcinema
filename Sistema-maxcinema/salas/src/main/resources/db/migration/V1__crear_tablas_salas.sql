CREATE TABLE sala (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    numero_de_sala INT NOT NULL DEFAULT 1
);
 
CREATE TABLE tipo_sala (
    id      INT AUTO_INCREMENT PRIMARY KEY,
    nombre  VARCHAR(50) NOT NULL
);
 
CREATE TABLE tipos_sala (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    nombre       VARCHAR(50) NOT NULL,
    sala_id      INT,
    TipoSala_id  INT,
    CONSTRAINT fk_tipossalas_sala     FOREIGN KEY (sala_id)     REFERENCES Sala(id),
    CONSTRAINT fk_tipossalas_tiposala FOREIGN KEY (TipoSala_id) REFERENCES tipo_sala(id)
);

CREATE TABLE tipo_asiento (
    id    INT AUTO_INCREMENT PRIMARY KEY,
    tipo  VARCHAR(50) NOT NULL
);

CREATE TABLE asientos (
    id               INT AUTO_INCREMENT PRIMARY KEY,
    fila             INT         NOT NULL,
    columna          VARCHAR(1)  NOT NULL,
    estado           BOOLEAN     NOT NULL,
    tipo_asiento_id  INT,
    CONSTRAINT fk_asiento_tipo FOREIGN KEY (tipo_asiento_id) REFERENCES tipo_asiento(id)
);

INSERT INTO 
    sala (nombre, numero_de_sala) 
VALUES 
    ('Sala Palco Premier', 1),
    ('Sala 4DX', 2),
    ('Sala 2D', 3);

INSERT INTO 
    tipo_sala (nombre) 
VALUES 
    ('Palco Premier'),
    ('4DX'),
    ('2D Tradicional');

INSERT INTO 
    tipos_sala (nombre, sala_id, TipoSala_id) 
VALUES 
    ('Asig Palco Premier', 1, 1),
    ('Asig 4DX', 2, 2),
    ('Asig 2D', 3, 3);

INSERT INTO 
    tipo_asiento (tipo) 
VALUES 
('Normal'),
('Reclinable'),
('D-Box con Movimiento');

INSERT INTO 
    asientos (fila, columna, estado, tipo_asiento_id) 
VALUES 
(1, 'A', TRUE, 1),
(1, 'B', FALSE, 2),
(1, 'C', TRUE, 3);