# Proyecto Transversal - Marketplace Fullstack

## 📋 Descripción
Plataforma de marketplace para la asignatura Desarrollo Fullstack I. 
Sistema de gestión de compra/venta de productos con arquitectura de microservicios.

## 👥 Integrantes
- Nicolas Herrera Labra
- Danae

## 🛠️ Tecnologías
- **Backend:** Spring Boot, Spring Cloud (Eureka, Gateway)
- **Base de datos:** MySQL
- **Control de versiones:** GitHub

## 🏗️ Microservicios (10+)
| Servicio | Puerto | Estado |
|----------|--------|--------|
| Eureka Server | 8761 | 📅 Pendiente |
| API Gateway | 8080 | 📅 Pendiente |
| Usuarios | 8081 | 📅 Pendiente |
| Productos | 8082 | 📅 Pendiente |
| Pedidos | 8083 | 📅 Pendiente |
| Carrito | 8084 | 📅 Pendiente |
| Inventario | 8085 | 📅 Pendiente |
| Pago | 8086 | 📅 Pendiente |
| Valoración | 8087 | 📅 Pendiente |
| Notificación | 8088 | 📅 Pendiente |
| Vendedor | 8089 | 📅 Pendiente |

### Microservicios (10 independientes)

| # | Microservicio | Puerto | Base de datos | Responsabilidad |
|---|---------------|--------|---------------|-----------------|
| 1 | **Usuarios** | 8081 | `db_usuarios` | Registro, login, roles (ADMIN/VENDEDOR/COMPRADOR) |
| 2 | **Vendedor** | 8082 | `db_vendedores` | Perfil de vendedor, reputación |
| 3 | **Producto** | 8083 | `db_productos` | CRUD de productos, búsqueda por categoría/precio |
| 4 | **Inventario** | 8084 | `db_inventario` | Control de stock, reserva de productos |
| 5 | **Carrito** | 8085 | `db_carritos` | Agregar/quitar productos, calcular subtotal |
| 6 | **Pedido** | 8086 | `db_pedidos` | Crear pedidos, historial de compras |
| 7 | **Pago** | 8087 | `db_pagos` | Simular pagos, generar comprobantes |
| 8 | **Valoración** | 8088 | `db_valoraciones` | Calificaciones y comentarios de productos |
| 9 | **Notificación** | 8089 | `db_notificaciones` | Envío de emails/sms (simulado) |
| 10 | **Reporte** | 8090 | `db_reportes` | Reportes de ventas, productos más vendidos |

## 🚀 Cómo ejecutar (próximamente)
