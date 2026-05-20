package com.marketplace.pedido.service;

import com.marketplace.pedido.DTO.PedidoRequestDTO;
import com.marketplace.pedido.DTO.PedidoResponseDTO;
import com.marketplace.pedido.DTO.ProductoClientDTO;
import com.marketplace.pedido.model.Pedido;
import com.marketplace.pedido.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final WebClient productoWebClient; // Inyectamos el WebClient configurado

    private PedidoResponseDTO makeToPedidoResponseDTO(Pedido pedido) {
        return new PedidoResponseDTO(pedido.getId(), pedido.getNomProducto(), pedido.getTipoProducto(), pedido.getPrecio());
    }

    // Método que consulta el microservicio de Producto para validar que existe y tiene stock
    private ProductoClientDTO obtenerProducto(Long productoId) {
        log.info("Consultando microservicio Producto para verificar producto con id: {}", productoId);
        try {
            return productoWebClient.get()
                    .uri("/productos/{id}", productoId) // GET http://localhost:8084/productos/{id}
                    .retrieve()
                    .bodyToMono(ProductoClientDTO.class)
                    .block(); // block() convierte la llamada reactiva a síncrona
        } catch (WebClientResponseException.NotFound e) {
            log.error("Producto con id {} no encontrado en el microservicio de Producto", productoId);
            throw new IllegalArgumentException("El producto con id " + productoId + " no existe");
        } catch (Exception e) {
            log.error("Error al comunicarse con el microservicio de Producto: {}", e.getMessage());
            throw new IllegalArgumentException("No se pudo verificar el producto. Intenta nuevamente.");
        }
    }

    public List<PedidoResponseDTO> findAllPedidos() {
        log.info("Se listan todos los pedidos");
        return pedidoRepository.findAll().stream()
                .map(this::makeToPedidoResponseDTO)
                .collect(Collectors.toList());
    }

    public PedidoResponseDTO findPedidoById(long id) {
        log.info("Se busca pedido con id: {}", id);
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado con id: " + id));
        return makeToPedidoResponseDTO(pedido);
    }

    public PedidoResponseDTO makePedido(PedidoRequestDTO newPedido) {
        log.info("Se inicia la creación de pedido para productoId: {}", newPedido.getProductoId());

        // Comunicación con microservicio Producto: verifica que el producto existe y tiene stock
        ProductoClientDTO producto = obtenerProducto(newPedido.getProductoId());

        if (!producto.isActivo()) {
            throw new IllegalArgumentException("El producto con id " + newPedido.getProductoId() + " no está activo");
        }
        if (producto.getStock() <= 0) {
            throw new IllegalArgumentException("El producto con id " + newPedido.getProductoId() + " no tiene stock disponible");
        }

        log.info("Producto verificado: {}. Stock disponible: {}", producto.getNombre(), producto.getStock());

        Pedido pedido = new Pedido();
        pedido.setNomProducto(newPedido.getNomProducto());
        pedido.setTipoProducto(newPedido.getTipoProducto());
        pedido.setPrecio(newPedido.getPrecio());
        pedido = pedidoRepository.save(pedido);

        log.info("Pedido creado exitosamente con id: {}", pedido.getId());
        return makeToPedidoResponseDTO(pedido);
    }

    public void deletePedido(long id) {
        log.info("Se elimina pedido con id: {}", id);
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado con id: " + id));
        pedidoRepository.delete(pedido);
    }
}
