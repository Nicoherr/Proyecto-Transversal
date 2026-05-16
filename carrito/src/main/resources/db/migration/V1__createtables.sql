-- Tabla principal del carrito, pertenece a un usuario de otro microservicio
CREATE TABLE carrito (
    id         BIGINT NOT NULL AUTO_INCREMENT,
    usuario_id BIGINT NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

-- Tabla intermedia entre carrito y producto
-- carrito_id es llave foránea que apunta a carrito
-- producto_id es solo un Long porque producto es otro microservicio
CREATE TABLE carrito_producto (
    id         BIGINT NOT NULL AUTO_INCREMENT,
    carrito_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    cantidad   INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_carrito FOREIGN KEY (carrito_id) REFERENCES carrito(id)
) ENGINE=InnoDB;