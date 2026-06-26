
CREATE TABLE comuna (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(25) NOT NULL
);

CREATE TABLE region (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(25) NOT NULL
);

CREATE TABLE cine (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(25) NOT NULL,
    direccion VARCHAR(50) NOT NULL,
    comuna_id INT,
    region_id INT,
    FOREIGN KEY (comuna_id) REFERENCES comuna(id),
    FOREIGN KEY (region_id) REFERENCES region(id)
);

INSERT INTO 
    region (nombre) 
VALUES 
    ('Metropolitana'),
    ('Valparaíso'),
    ('Biobío');

INSERT INTO
    comuna (nombre) 
VALUES 
    ('Santiago Centro'),
    ('Viña del Mar'),
    ('Concepción');

INSERT INTO 
    cine (nombre, direccion, comuna_id, region_id) 
VALUES 
    ('MaxCinema Santiago', 'Av. Américo Vespucio 1501', 1, 1),
    ('MaxCinema Viña', 'Av. Libertad 1348', 2, 2),
    ('MaxCinema Concepción', 'Av. los Carrera Poniente 301', 3, 3);