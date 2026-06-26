CREATE TABLE pelicula (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(50) NOT NULL,
    descripcion VARCHAR(250) NOT NULL,
    duracion INT,
    anioEstreno INT,
    estado VARCHAR(20)
);

CREATE TABLE genero (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE generos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pelicula_id INT NOT NULL,
    genero_id INT NOT NULL,
    CONSTRAINT fk_generos_pelicula FOREIGN KEY (pelicula_id) REFERENCES pelicula(id),
    CONSTRAINT fk_generos_genero FOREIGN KEY (genero_id) REFERENCES genero(id)
);

CREATE TABLE director (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    nacionalidad VARCHAR(50) NOT NULL
);

CREATE TABLE directores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    director_id INT NOT NULL,
    pelicula_id INT NOT NULL,
    CONSTRAINT fk_directores_director FOREIGN KEY (director_id) REFERENCES director(id),
    CONSTRAINT fk_directores_pelicula FOREIGN KEY (pelicula_id) REFERENCES pelicula(id)
);

CREATE TABLE idioma (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL
);

CREATE TABLE idiomas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pelicula_id INT NOT NULL,
    idioma_id INT NOT NULL,
    CONSTRAINT fk_idiomas_pelicula FOREIGN KEY (pelicula_id) REFERENCES pelicula(id),
    CONSTRAINT fk_idiomas_idioma FOREIGN KEY (idioma_id) REFERENCES idioma(id)
);

CREATE TABLE salasPelicula (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pelicula_id INT NOT NULL,
    sala_id INT NOT NULL,
    CONSTRAINT fk_salaspelicula_pelicula FOREIGN KEY (pelicula_id) REFERENCES pelicula(id)
);

INSERT INTO 
    pelicula (titulo, descripcion, duracion, anioEstreno, estado) 
VALUES 
    ('La Odisea', 'Relato de antiguos mitos y leyendas griegas.', 120, 2026, 'Pre-Venta'),
    ('Scary Movie 6', 'Comedia satírica a películas de terror.', 95, 2026, 'Cartelera'),
    ('Spider-Man: Un Nuevo día', 'El renacer de peter parker como Spider-Man.', 105, 2026, 'Próximamente');

INSERT INTO 
    genero (nombre) 
VALUES 
    ('Ciencia Ficción'),
    ('Comedia'),
    ('Terror');

INSERT INTO 
    generos (pelicula_id, genero_id) 
VALUES 
    (1, 1),
    (2, 2),
    (3, 3); 

INSERT INTO 
    director (nombre, nacionalidad) 
VALUES 
    ('Christopher Nolan', 'Británica'),
    ('Michael Tiddes', 'Estadounidense'),
    ('Destin Cretton', 'Estadounidense');

INSERT INTO 
    directores (director_id, pelicula_id)
VALUES 
    (1, 1),
    (2, 2),
    (3, 3);

INSERT INTO 
    idioma (nombre) 
VALUES 
    ('Español Latino'),
    ('Inglés Subtitulado'),
    ('Inglés');

INSERT INTO 
    idiomas (pelicula_id, idioma_id) 
VALUES 
    (1, 2),
    (2, 1),
    (3, 2);

INSERT INTO 
    salasPelicula (pelicula_id, sala_id)
VALUES 
    (1, 1),
    (2, 2),
    (3, 3);