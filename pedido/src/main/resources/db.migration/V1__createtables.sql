CREATE TABLE IF NOT EXISTS pedido (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom_producto VARCHAR(200) NOT NULL,
    tipo_producto VARCHAR(30) NOT NULL,
    precio INT NOT NULL
    );