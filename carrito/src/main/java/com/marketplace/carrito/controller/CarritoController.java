package com.marketplace.carrito.controller;
import com.marketplace.carrito.model.*;
import com.marketplace.carrito.service.CarritoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    private final CarritoService service;

    public CarritoController(CarritoService service) {
        this.service = service;
    }

    @PostMapping("/crear")
    public Carrito crear(@RequestParam Long usuarioId) {
        return service.crearCarrito(usuarioId);
    }

    @GetMapping("/{carritoId}")
    public List<CarritoProducto> ver(@PathVariable Long carritoId) {
        return service.verCarrito(carritoId);
    }

    @PostMapping("/{carritoId}/agregar")
    public CarritoProducto agregar(
            @PathVariable Long carritoId,
            @RequestParam Long productoId,
            @RequestParam int cantidad) {
        return service.agregarProducto(carritoId, productoId, cantidad);
    }

    @DeleteMapping("/producto/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminarProducto(id);
    }

    @PutMapping("/producto/{id}")
    public CarritoProducto actualizar(
            @PathVariable Long id,
            @RequestParam int cantidad) {
        return service.actualizarCantidad(id, cantidad);
    }
}

