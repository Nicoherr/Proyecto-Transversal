package com.marketplace.inventario.service;
import com.marketplace.inventario.model.Inventario;
import com.marketplace.inventario.repository.InventarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioService {

    private final InventarioRepository repository;

    public InventarioService(InventarioRepository repository) {
        this.repository = repository;
    }

    public List<Inventario> listar() {
        return repository.findAll();
    }

    public Inventario crear(Inventario inventario) {
        return repository.save(inventario);
    }

    public Inventario obtenerPorProducto(Long productoId) {
        return repository.findByProductoId(productoId)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
    }

    public Inventario actualizarStock(Long productoId, int cantidad) {
        Inventario inv = obtenerPorProducto(productoId);

        if (inv.getStock() + cantidad < 0) {
            throw new RuntimeException("Stock insuficiente");
        }

        inv.setStock(inv.getStock() + cantidad);

        return repository.save(inv);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    public boolean stockBajo(Long productoId) {
        Inventario inv = obtenerPorProducto(productoId);
        return inv.getStock() <= inv.getStockMinimo();
    }
}

