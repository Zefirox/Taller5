CREATE TYPE role_type as enum(
	'Artista',
	'Comprador'
);


CREATE TABLE Usuarios(
                         nombre varchar(15),
                         apellido varchar(15),
                         usuario varchar(20) PRIMARY KEY,
                         password varchar,
                         fcoins INT,
                         rol role_type DEFAULT null
);

CREATE TABLE colecciones (
                             coleccion_id SERIAL PRIMARY KEY,
                             nombre VARCHAR
);

CREATE TABLE Arte(
                     usuario varchar(20),
                     FOREIGN KEY (usuario)
                         REFERENCES Usuarios (usuario),
                     titulo varchar(20),
                     nombreArchivo varchar PRIMARY KEY,
                     precio INT,
                     coleccion_id INT,
                     FOREIGN KEY (coleccion_id)
                         REFERENCES colecciones (coleccion_id)

);

CREATE TABLE Likes (
                       like_id SERIAL PRIMARY KEY,
                       usuario varchar(20),
                       FOREIGN KEY (usuario)
                           REFERENCES Usuarios (usuario),
                       nombreArchivo varchar,
                       FOREIGN KEY (nombreArchivo)
                           REFERENCES Arte (nombreArchivo)

);