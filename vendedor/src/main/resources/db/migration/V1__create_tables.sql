-- Tabla vendedor — cada vendedor pertenece a un usuario de otro microservicio
-- usuario_id es Long porque usuario es otro microservicio
CREATE TABLE vendedor (
    id                   BIGINT NOT NULL AUTO_INCREMENT,
    nombre_tienda        VARCHAR(100) NOT NULL,
    descripcion          VARCHAR(255),
    reputacion           DOUBLE NOT NULL DEFAULT 0.0,
    cantidad_valoraciones INT NOT NULL DEFAULT 0,
    usuario_id           BIGINT NOT NULL,
    activo               BIT NOT NULL DEFAULT 1,
    PRIMARY KEY (id)
) ENGINE=InnoDB;