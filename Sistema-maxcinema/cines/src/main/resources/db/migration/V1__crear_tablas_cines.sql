
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
