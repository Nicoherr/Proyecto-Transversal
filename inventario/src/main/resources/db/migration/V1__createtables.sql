-- Tabla de inventario — cada registro representa el stock de un producto
-- producto_id es Long porque producto es otro microservicio
CREATE TABLE inventario (
    id           BIGINT NOT NULL AUTO_INCREMENT,
    producto_id  BIGINT NOT NULL,
    stock        INT NOT NULL,
    stock_minimo INT NOT NULL DEFAULT 5,
    PRIMARY KEY (id)
) ENGINE=InnoDB;