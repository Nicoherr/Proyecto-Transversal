CREATE TABLE detalle_reporte
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    observacion VARCHAR(512) NOT NULL,
    valor       INT          NOT NULL,
    reporte_id  BIGINT       NOT NULL,
    CONSTRAINT pk_detalle_reporte PRIMARY KEY (id)
);

CREATE TABLE reporte
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    tipo        VARCHAR(100) NOT NULL,
    descripcion VARCHAR(512) NOT NULL,
    fecha       datetime     NOT NULL,
    estado      BIT(1)       NOT NULL,
    CONSTRAINT pk_reporte PRIMARY KEY (id)
);

ALTER TABLE detalle_reporte
    ADD CONSTRAINT FK_DETALLE_REPORTE_ON_REPORTE FOREIGN KEY (reporte_id) REFERENCES reporte (id);