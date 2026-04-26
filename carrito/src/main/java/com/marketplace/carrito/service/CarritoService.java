package com.marketplace.carrito.service;
import com.marketplace.carrito.model.*;
import com.marketplace.carrito.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final CarritoProductoRepository productoRepository;

    public CarritoService(CarritoRepository carritoRepository,
                          CarritoProductoRepository productoRepository) {
        this.carritoRepository = carritoRepository;
        this.productoRepository = productoRepository;
    }

    public Carrito crearCarrito(Long usuarioId) {
        return carritoRepository.save(new Carrito(null, usuarioId));
    }

    public List<CarritoProducto> verCarrito(Long carritoId) {
        return productoRepository.findByCarritoId(carritoId);
    }

    public CarritoProducto agregarProducto(Long carritoId, Long productoId, int cantidad) {

        CarritoProducto existente = productoRepository
                .findByCarritoIdAndProductoId(carritoId, productoId)
                .orElse(null);

        if (existente != null) {
            existente.setCantidad(existente.getCantidad() + cantidad);
            return productoRepository.save(existente);
        }

        CarritoProducto nuevo = new CarritoProducto(null, carritoId, productoId, cantidad);
        return productoRepository.save(nuevo);
    }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    public CarritoProducto actualizarCantidad(Long id, int cantidad) {
        CarritoProducto cp = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        cp.setCantidad(cantidad);
        return productoRepository.save(cp);
    }
}

