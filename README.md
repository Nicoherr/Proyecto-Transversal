# Marketplace Fullstack I - Proyecto Transversal

## 📋 Descripción
Plataforma de marketplace desarrollada como proyecto semestral para la asignatura **Desarrollo Fullstack I**.  
El sistema permite a vendedores publicar productos y a compradores realizar compras, utilizando una **arquitectura de microservicios** con comunicación directa mediante **RestTemplate**.

**Nota:** Este proyecto es exclusivamente backend. No incluye interfaz de usuario. Las APIs se prueban mediante Postman.

## 👥 Integrantes
- Nicolas Herrera
- Danae Aliante

## 🛠️ Tecnologías utilizadas
| Tecnología | Propósito |
|------------|-----------|
| Spring Boot 3.x | Framework backend |
| Java 17+ | Lenguaje de programación |
| MySQL | Base de datos (una por microservicio) |
| Maven | Gestor de dependencias |
| RestTemplate | Comunicación entre microservicios |
| JWT | Autenticación y seguridad |
| BCrypt | Encriptación de contraseñas |
| Postman | Pruebas de APIs |
| GitHub | Control de versiones |

## 🏗️ Microservicios (10 independientes)

| # | Microservicio | Puerto | Base de datos | Responsabilidad |
|---|---------------|--------|---------------|-----------------|
| 1 | Usuarios | 8081 | `db_usuarios` | Registro, login, roles |
| 2 | Vendedor | 8082 | `db_vendedores` | Perfil de vendedor, reputación |
| 3 | Producto | 8083 | `db_productos` | CRUD de productos |
| 4 | Inventario | 8084 | `db_inventario` | Control de stock |
| 5 | Carrito | 8085 | `db_carritos` | Agregar/quitar productos |
| 6 | Pedido | 8086 | `db_pedidos` | Crear pedidos, historial |
| 7 | Pago | 8087 | `db_pagos` | Simular pagos |
| 8 | Valoración | 8088 | `db_valoraciones` | Calificaciones de productos |
| 9 | Notificación | 8089 | `db_notificaciones` | Envío de alertas (simulado) |
| 10 | Reporte | 8090 | `db_reportes` | Reportes de ventas |

## 📁 Estructura del proyecto
