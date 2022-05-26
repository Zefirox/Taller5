INSERT INTO usuarios (nombre,apellido,usuario, password, fcoins, rol) VALUES ('Gabriel', 'Maldonado', 'gmaldo', '123456', 600, 'Comprador');
INSERT INTO usuarios (nombre,apellido,usuario, password, fcoins, rol) VALUES ('Cristian', 'Cruz', 'cristianc', '654321', 0, 'Artista');
INSERT INTO arte (usuario, titulo, nombrearchivo, precio, coleccion_id) VALUES ('cristianc', 'mi primer arte', 'jskulrfo456apt25.jpg', '520', '1');
INSERT INTO arte (usuario, titulo, nombrearchivo, precio, coleccion_id) VALUES ('cristianc', 'mi segundo arte', 'Jgt639Hrdjpl8h9L.png', '800', '1');
INSERT INTO colecciones (coleccion_id, nombre) VALUES ('1', 'coleccion1');