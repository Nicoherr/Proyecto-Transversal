-- Tabla producto — cada producto pertenece a un vendedor de otro microservicio
-- vendedor_id es Long porque vendedor es otro microservicio
CREATE TABLE producto (
    id          BIGINT NOT NULL AUTO_INCREMENT,
    nombre      VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    precio      DOUBLE NOT NULL,
    stock       INT NOT NULL,
    vendedor_id BIGINT NOT NULL,
    activo      BIT NOT NULL DEFAULT 1,
    PRIMARY KEY (id)
) ENGINE=InnoDB;