CREATE TABLE IF NOT EXISTS valoracion (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     num_estrella INT NOT NULL,
     recomendacion VARCHAR(512) NOT NULL
    );