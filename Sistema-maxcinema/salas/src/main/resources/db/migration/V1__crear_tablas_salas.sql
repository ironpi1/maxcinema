CREATE TABLE sala (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    nombre        VARCHAR(50) NOT NULL,
    numeroDeSala  INT         NOT NULL DEFAULT 1
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

-- Insertar Salas
INSERT INTO 
    sala (nombre, numeroDeSala) 
VALUES 
    ('Sala Palco Premier', 1),
    ('Sala 4DX', 2),
    ('Sala 2D', 3);

-- Insertar Tipos de Sala
INSERT INTO 
    tipo_sala (nombre) 
VALUES 
    ('Palco Premier'),
    ('4DX'),
    ('2D Tradicional');

-- Relacionar Salas con Tipos de Sala (Tabla intermedia)
INSERT INTO 
    tipos_sala (nombre, sala_id, TipoSala_id) 
VALUES 
    ('Asig Palco Premier', 1, 1),
    ('Asig 4DX', 2, 2),
    ('Asig 2D', 3, 3);

-- Insertar Tipos de Asiento
INSERT INTO 
    tipo_asiento (tipo) 
VALUES 
('Normal'),
('Reclinable'),
('D-Box con Movimiento');

-- Insertar Asientos (estado en BOOLEAN: true = disponible, false = ocupado/bloqueado)
INSERT INTO 
    asientos (fila, columna, estado, tipo_asiento_id) 
VALUES 
(1, 'A', TRUE, 1),
(1, 'B', FALSE, 2),
(1, 'C', TRUE, 3);