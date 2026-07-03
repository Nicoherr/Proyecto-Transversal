package com.marketplace.pago.service;

import com.marketplace.pago.DTO.PagoRequestDTO;
import com.marketplace.pago.DTO.PagoResponseDTO;
import com.marketplace.pago.DTO.PedidoClientDTO;
import com.marketplace.pago.model.Pago;
import com.marketplace.pago.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PagoService {

    private final PagoRepository pagoRepository;
    private final WebClient pedidoWebClient;

    private static final Set<String> METODOS_PERMITIDOS = Set.of(
            "Tarjeta de crédito", "Tarjeta de débito", "Transferencia bancaria", "PayPal"
    );

    // ─── Mapper ───────────────────────────────────────────────────────────────
    private PagoResponseDTO toDTO(Pago pago) {
        return new PagoResponseDTO(
                pago.getId(),
                pago.getPedidoId(),
                pago.getMetodoPago(),
                pago.getComprobante(),
                pago.getFecha()
        );
    }

    private Pago obtenerOFallar(long id) {
        return pagoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pago no encontrado con id: " + id));
    }

    // ─── Comunicación con microservicio Pedido ─────────────────────────────────
    private PedidoClientDTO obtenerPedido(Long pedidoId) {
        log.info("Consultando microservicio Pedido para id: {}", pedidoId);
        try {
            return pedidoWebClient.get()
                    .uri("/pedidos/{id}", pedidoId)
                    .retrieve()
                    .bodyToMono(PedidoClientDTO.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            log.error("Pedido con id {} no encontrado", pedidoId);
            throw new IllegalArgumentException("El pedido con id " + pedidoId + " no existe.");
        } catch (Exception e) {
            log.error("Microservicio Pedido no disponible: {}", e.getMessage());
            throw new IllegalArgumentException(
                    "El servicio de pedidos no está disponible en este momento. Intenta más tarde."
            );
        }
    }

    // ─── CRUD ─────────────────────────────────────────────────────────────────
    public List<PagoResponseDTO> findAllPagos() {
        log.info("Se listan todos los pagos");
        return pagoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PagoResponseDTO findPagosById(long id) {
        log.info("Se busca pago con id: {}", id);
        return toDTO(obtenerOFallar(id));
    }

    public PagoResponseDTO makePago(PagoRequestDTO dto) {
        log.info("Creando pago para pedidoId: {}", dto.getPedidoId());

        // ── Regla 1: El método de pago debe ser uno de los permitidos ─────────
        if (!METODOS_PERMITIDOS.contains(dto.getMetodoPago())) {
            throw new IllegalArgumentException(
                    "Método de pago inválido. Los métodos permitidos son: "
                            + String.join(", ", METODOS_PERMITIDOS)
            );
        }

        // ── Regla 2: No se puede pagar dos veces el mismo pedido ─────────────
        if (pagoRepository.existsByPedidoId(dto.getPedidoId())) {
            log.warn("Intento de pagar dos veces el pedido id: {}", dto.getPedidoId());
            throw new IllegalArgumentException(
                    "El pedido con id " + dto.getPedidoId() + " ya tiene un pago registrado."
            );
        }

        // ── Verifica que el pedido existe (WebClient al final) ────────────────
        PedidoClientDTO pedido = obtenerPedido(dto.getPedidoId());

        // ── Regla 3: El precio del pedido debe ser mayor a 0 ─────────────────
        if (pedido.getPrecio() <= 0) {
            throw new IllegalArgumentException(
                    "No se puede procesar un pago para un pedido con precio inválido."
            );
        }

        log.info("Pedido '{}' verificado. Procesando pago.", pedido.getNomProducto());

        String comprobante = "COMP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Pago pago = new Pago();
        pago.setPedidoId(dto.getPedidoId());
        pago.setMetodoPago(dto.getMetodoPago());
        pago.setComprobante(comprobante);
        pago.setFecha(new Date());
        pago = pagoRepository.save(pago);

        log.info("Pago creado con comprobante: {}", comprobante);
        return toDTO(pago);
    }

    public void deletePago(long id) {
        log.info("Eliminando pago con id: {}", id);
        pagoRepository.delete(obtenerOFallar(id));
        log.info("Pago con id: {} eliminado exitosamente", id);
    }
}

