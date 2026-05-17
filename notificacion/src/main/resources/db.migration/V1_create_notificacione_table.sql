CREATE TABLE IF NOT EXISTS notificacion ( --Crea la tabla 'notificacion' si no existe ya
    id BIGINT AUTO_INCREMENT PRIMARY KEY, -- Columna: número único, automático y clave principal
    asunto VARCHAR(100) NOT NULL, -- Asunto del mensaje (máx. 100 caracteres, obligatorio)
    mensaje VARCHAR(512) NOT NULL, -- Cuerpo del mensaje (máx. 512 caracteres, obligatorio)
    fecha DATETIME NOT NULL -- Fecha y hora del mensaje (obligatorio)
    );
