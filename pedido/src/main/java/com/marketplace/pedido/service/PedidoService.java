package com.marketplace.pedido.service;

import com.marketplace.pedido.DTO.PedidoRequestDTO;
import com.marketplace.pedido.DTO.PedidoResponseDTO;
import com.marketplace.pedido.DTO.ProductoClientDTO;
import com.marketplace.pedido.model.Pedido;
import com.marketplace.pedido.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final WebClient productoWebClient;

    // ─── Mapper ───────────────────────────────────────────────────────────────
    private PedidoResponseDTO toDTO(Pedido pedido) {
        return new PedidoResponseDTO(pedido.getId(), pedido.getNomProducto(), pedido.getTipoProducto(), pedido.getPrecio());
    }

    // ─── Comunicación con microservicio Producto ───────────────────────────────
    private ProductoClientDTO obtenerProducto(Long productoId) {
        log.info("Consultando microservicio Producto para verificar producto con id: {}", productoId);
        try {
            return productoWebClient.get()
                    .uri("/api/producto/{id}", productoId)
                    .retrieve()
                    .bodyToMono(ProductoClientDTO.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            log.error("Producto con id {} no encontrado en el microservicio de Producto", productoId);
            throw new IllegalArgumentException("El producto con id " + productoId + " no existe.");
        } catch (Exception e) {
            log.error("Error al comunicarse con el microservicio de Producto: {}", e.getMessage());
            throw new IllegalArgumentException("No se pudo verificar el producto. Intenta nuevamente.");
        }
    }

    // ─── CRUD ─────────────────────────────────────────────────────────────────
    public List<PedidoResponseDTO> findAllPedidos() {
        log.info("Se listan todos los pedidos");
        return pedidoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PedidoResponseDTO findPedidoById(long id) {
        log.info("Se busca pedido con id: {}", id);
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado con id: " + id));
        return toDTO(pedido);
    }

    public PedidoResponseDTO makePedido(PedidoRequestDTO dto) {
        log.info("Se inicia la creación de pedido para productoId: {}", dto.getProductoId());

        // ── Regla 1: El precio debe ser mayor a 0 ────────────────────────────
        if (dto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio del pedido debe ser mayor a 0.");
        }

        // ── Regla 2: El nombre del producto no puede estar vacío ─────────────
        if (dto.getNomProducto() == null || dto.getNomProducto().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio.");
        }

        // ── Interacción: verificar que el producto existe, está activo y tiene stock ──
        ProductoClientDTO producto = obtenerProducto(dto.getProductoId());

        if (!producto.getActivo()) {
            log.warn("Intento de pedir producto inactivo con id: {}", dto.getProductoId());
            throw new IllegalArgumentException("El producto '" + producto.getNombre() + "' no está disponible para pedidos.");
        }

        if (producto.getStock() <= 0) {
            log.warn("Intento de pedir producto sin stock con id: {}", dto.getProductoId());
            throw new IllegalArgumentException("El producto '" + producto.getNombre() + "' no tiene stock disponible.");
        }

        // ── Regla 3: El precio enviado no puede diferir más de un 10% del precio real ──
        double diferencia = Math.abs(dto.getPrecio() - producto.getPrecio()) / producto.getPrecio();
        if (diferencia > 0.10) {
            log.warn("Precio enviado (${}) difiere del precio real (${}) en más de un 10%", dto.getPrecio(), producto.getPrecio());
            throw new IllegalArgumentException(
                    "El precio enviado no coincide con el precio del producto. Precio actual: $" + producto.getPrecio()
            );
        }

        log.info("Producto '{}' verificado. Stock: {}. Creando pedido.", producto.getNombre(), producto.getStock());

        Pedido pedido = new Pedido();
        pedido.setNomProducto(dto.getNomProducto().trim());
        pedido.setTipoProducto(dto.getTipoProducto());
        pedido.setPrecio(dto.getPrecio());
        pedido.setDireccionEntrega(dto.getDireccionEntrega());
        pedido = pedidoRepository.save(pedido);

        log.info("Pedido creado exitosamente con id: {} para producto '{}'", pedido.getId(), producto.getNombre());
        return toDTO(pedido);
    }

    public void deletePedido(long id) {
        log.info("Se elimina pedido con id: {}", id);
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado con id: " + id));
        pedidoRepository.delete(pedido);
        log.info("Pedido con id: {} eliminado exitosamente", id);
    }
}