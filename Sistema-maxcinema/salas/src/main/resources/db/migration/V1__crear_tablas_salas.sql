CREATE TABLE sala (
    id            INT AUTO_INCREMENT PRIMARY KEY,
    nombre        VARCHAR(12) NOT NULL,
    numeroDeSala  INT         NOT NULL DEFAULT 1
);
 
CREATE TABLE tipo_sala (
    id      INT AUTO_INCREMENT PRIMARY KEY,
    nombre  VARCHAR(17) NOT NULL
);
 
CREATE TABLE tipos_sala (
    id           INT AUTO_INCREMENT PRIMARY KEY,
    nombre       VARCHAR(14) NOT NULL,
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