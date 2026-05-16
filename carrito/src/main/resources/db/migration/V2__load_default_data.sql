-- Insertamos un carrito de prueba para el usuario con ID 1
INSERT INTO carrito (usuario_id) VALUES (1);

-- Le agregamos un producto de prueba al carrito recién creado
INSERT INTO carrito_producto (carrito_id, producto_id, cantidad) VALUES (1, 1, 2);