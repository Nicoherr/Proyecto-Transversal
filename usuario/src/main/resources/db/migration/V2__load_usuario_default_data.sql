INSERT INTO usuario (nombre, email, password, rol, activo)
VALUES (
    'Administrador',
    'admin@marketplace.cl',
    -- Este es "admin123" encriptado con BCrypt
    '$2a$12$eZJCgmVJxTGkMCBaFzqFJOyMUoGMJiCkTGHzqFJiCkTGHzqFJ',
    'ADMIN',
    1
);