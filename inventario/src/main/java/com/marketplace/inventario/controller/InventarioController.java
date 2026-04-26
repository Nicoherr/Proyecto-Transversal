package com.marketplace.inventario.controller;
import com.marketplace.inventario.model.Inventario;
import com.marketplace.inventario.service.InventarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
public class InventarioController {

    private final InventarioService service;

    public InventarioController(InventarioService service) {
        this.service = service;
    }

    @GetMapping
    public List<Inventario> listar() {
        return service.listar();
    }

    @PostMapping
    public Inventario crear(@RequestBody Inventario inventario) {
        return service.crear(inventario);
    }

    @GetMapping("/producto/{productoId}")
    public Inventario obtener(@PathVariable Long productoId) {
        return service.obtenerPorProducto(productoId);
    }

    @PutMapping("/producto/{productoId}")
    public Inventario actualizarStock(@PathVariable Long productoId,
                                      @RequestParam int cantidad) {
        return service.actualizarStock(productoId, cantidad);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @GetMapping("/producto/{productoId}/stock-bajo")
    public boolean stockBajo(@PathVariable Long productoId) {
        return service.stockBajo(productoId);
    }
}
