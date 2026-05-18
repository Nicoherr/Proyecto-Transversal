package com.marketplace.carrito.service;

import com.marketplace.carrito.dto.CarritoRequestDTO;
import com.marketplace.carrito.dto.CarritoResponseDTO;
import com.marketplace.carrito.dto.CarritoProductoRequestDTO;
import com.marketplace.carrito.dto.CarritoProductoResponseDTO;
import com.marketplace.carrito.model.Carrito;
import com.marketplace.carrito.model.CarritoProducto;
import com.marketplace.carrito.repository.CarritoProductoRepository;
import com.marketplace.carrito.repository.CarritoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final CarritoProductoRepository carritoProductoRepository;

    public CarritoService(CarritoRepository carritoRepository, CarritoProductoRepository carritoProductoRepository) {
        this.carritoRepository = carritoRepository;
        this.carritoProductoRepository = carritoProductoRepository;
    }

    public CarritoResponseDTO crear(CarritoRequestDTO dto) {
        log.info("Creando un nuevo carrito para el Usuario ID: {}", dto.getUsuarioId());
        Carrito carrito = new Carrito();
        carrito.setUsuarioId(dto.getUsuarioId());
        Carrito guardado = carritoRepository.save(carrito);
        log.info("Carrito creado exitosamente con ID: {}", guardado.getId());
        return convertirAResponse(guardado);
    }

    public CarritoResponseDTO obtener(Long id) {
        log.info("Buscando carrito con ID: {}", id);
        Carrito c = carritoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado con id: " + id));
        log.info("Carrito encontrado para el Usuario ID: {}", c.getUsuarioId());
        return convertirAResponse(c);
    }

    public CarritoProductoResponseDTO agregarProducto(CarritoProductoRequestDTO dto) {
        log.info("Agregando {} unidades del Producto ID: {} al Carrito ID: {}",
                dto.getCantidad(), dto.getProductoId(), dto.getCarritoId());

        // Buscamos el carrito completo porque @ManyToOne necesita la entidad, no solo el ID
        Carrito carrito = carritoRepository.findById(dto.getCarritoId())
                .orElseThrow(() -> new RuntimeException("Carrito no encontrado con ID: " + dto.getCarritoId()));

        // Verificamos si el producto ya existe en el carrito
        Optional<CarritoProducto> productoExistente = carritoProductoRepository
                .findByCarrito_IdAndProductoId(dto.getCarritoId(), dto.getProductoId());

        CarritoProducto guardado;
        if (productoExistente.isPresent()) {
            // Si ya existe, solo sumamos la cantidad nueva a la que había
            CarritoProducto cp = productoExistente.get();
            cp.setCantidad(cp.getCantidad() + dto.getCantidad());
            guardado = carritoProductoRepository.save(cp);
            log.info("Producto ya existía en el carrito. Cantidad actualizada a: {}", guardado.getCantidad());
        } else {
            // Si no existe, lo creamos y le asignamos la entidad Carrito completa
            CarritoProducto nuevoCp = new CarritoProducto();
            nuevoCp.setCarrito(carrito); // ✅ Asignamos la entidad, no el ID
            nuevoCp.setProductoId(dto.getProductoId());
            nuevoCp.setCantidad(dto.getCantidad());
            guardado = carritoProductoRepository.save(nuevoCp);
            log.info("Producto agregado al carrito exitosamente con ID: {}", guardado.getId());
        }

        return convertirProductoAResponse(guardado);
    }

    public List<CarritoProductoResponseDTO> listarProductos(Long carritoId) {
        log.info("Listando productos del Carrito ID: {}", carritoId);
        return carritoProductoRepository.findByCarrito_Id(carritoId).stream()
                .map(this::convertirProductoAResponse)
                .collect(Collectors.toList());
    }

    private CarritoResponseDTO convertirAResponse(Carrito c) {
        CarritoResponseDTO res = new CarritoResponseDTO();
        res.setId(c.getId());
        res.setUsuarioId(c.getUsuarioId());
        return res;
    }

    private CarritoProductoResponseDTO convertirProductoAResponse(CarritoProducto cp) {
        CarritoProductoResponseDTO res = new CarritoProductoResponseDTO();
        res.setId(cp.getId());
        // Como ahora carrito es una entidad, obtenemos el ID así
        res.setCarritoId(cp.getCarrito().getId()); //  .getCarrito().getId()
        res.setProductoId(cp.getProductoId());
        res.setCantidad(cp.getCantidad());
        return res;
    }
}