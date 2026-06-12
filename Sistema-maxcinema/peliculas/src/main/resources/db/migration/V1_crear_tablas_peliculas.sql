CREATE TABLE pelicula (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR2(50) NOT NULL,
    descripcion VARCHAR2(250) NOT NULL,
    duracion INT,
    anioEstreno INT,
    estado VARCHAR2(20)
);

CREATE TABLE genero (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR2(50) NOT NULL
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
    nombre VARCHAR2(50) NOT NULL,
    nacionalidad VARCHAR(50) NOT NULL
);

CREATE TABLE directores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    director_id INT NOT NULL,
    pelicula_id INT NOT NULL,
    CONSTRAINT fk_directores_director FOREIGN KEY (director_id) REFERENCES director(id),
    CONSTRAINT fk_directores_pelicula FOREIGN KEY (pelicula_id) REFERENCES pelicula(id),
);

CREATE TABLE idioma (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR2(20) NOT NULL
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
)