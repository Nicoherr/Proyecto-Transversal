package com.marketplace.carrito.service;
import com.marketplace.carrito.dto.*;
import com.marketplace.carrito.model.Carrito;
import com.marketplace.carrito.model.CarritoProducto;
import com.marketplace.carrito.repository.CarritoRepository;
import com.marketplace.carrito.repository.CarritoProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final CarritoProductoRepository carritoProductoRepository;

    public CarritoService(CarritoRepository carritoRepository, CarritoProductoRepository carritoProductoRepository) {
        this.carritoRepository = carritoRepository;
        this.carritoProductoRepository = carritoProductoRepository;
    }

    // Lógica para el Carrito principal
    public CarritoResponseDTO crearCarrito(CarritoRequestDTO dto) {
        Carrito carrito = new Carrito();
        carrito.setUsuarioId(dto.getUsuarioId());
        return convertirCarritoAResponse(carritoRepository.save(carrito));
    }

    public CarritoResponseDTO obtenerCarrito(Long id) {
        Carrito c = carritoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado"));
        return convertirCarritoAResponse(c);
    }

    // Lógica para agregar productos al carrito
    public CarritoProductoResponseDTO agregarProducto(CarritoProductoRequestDTO dto) {
        // Validamos que el carrito exista
        if (!carritoRepository.existsById(dto.getCarritoId())) {
            throw new RuntimeException("El carrito no existe");
        }

        // Buscamos si el producto ya está en el carrito usando tu método personalizado
        Optional<CarritoProducto> existente = carritoProductoRepository
                .findByCarritoIdAndProductoId(dto.getCarritoId(), dto.getProductoId());

        CarritoProducto item;
        if (existente.isPresent()) {
            item = existente.get();
            item.setCantidad(item.getCantidad() + dto.getCantidad());
        } else {
            item = new CarritoProducto();
            item.setCarritoId(dto.getCarritoId());
            item.setProductoId(dto.getProductoId());
            item.setCantidad(dto.getCantidad());
        }

        return convertirItemAResponse(carritoProductoRepository.save(item));
    }

    public List<CarritoProductoResponseDTO> listarProductos(Long carritoId) {
        return carritoProductoRepository.findByCarritoId(carritoId).stream()
                .map(this::convertirItemAResponse)
                .collect(Collectors.toList());
    }

    // Mapeadores
    private CarritoResponseDTO convertirCarritoAResponse(Carrito c) {
        CarritoResponseDTO res = new CarritoResponseDTO();
        res.setId(c.getId());
        res.setUsuarioId(c.getUsuarioId());
        return res;
    }

    private CarritoProductoResponseDTO convertirItemAResponse(CarritoProducto cp) {
        CarritoProductoResponseDTO res = new CarritoProductoResponseDTO();
        res.setId(cp.getId());
        res.setCarritoId(cp.getCarritoId());
        res.setProductoId(cp.getProductoId());
        res.setCantidad(cp.getCantidad());
        return res;
    }
}

