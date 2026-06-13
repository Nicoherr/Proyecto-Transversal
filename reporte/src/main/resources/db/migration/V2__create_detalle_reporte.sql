CREATE TABLE IF NOT EXISTS detalle_reporte (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    observacion VARCHAR(512) NOT NULL,
    valor INT NOT NULL,
    reporte_id BIGINT NOT NULL,
    CONSTRAINT fk_detalle_reporte_reporte FOREIGN KEY (reporte_id) REFERENCES reporte (id)
);