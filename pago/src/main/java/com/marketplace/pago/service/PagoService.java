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
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PagoService {

    private final PagoRepository pagoRepository;
    private final WebClient pedidoWebClient;

    // Métodos de pago permitidos por el marketplace
    private static final Set<String> METODOS_PERMITIDOS = Set.of(
            "Tarjeta de crédito", "Tarjeta de débito", "Transferencia bancaria", "PayPal"
    );

    // ─── Mapper ───────────────────────────────────────────────────────────────
    private PagoResponseDTO toDTO(Pago pago) {
        return new PagoResponseDTO(pago.getId(), pago.getMetodoPago(), pago.getComprobante(), pago.getFecha());
    }

    // ─── Comunicación con microservicio Pedido ─────────────────────────────────
    private PedidoClientDTO obtenerPedido(Long pedidoId) {
        log.info("Consultando microservicio Pedido para verificar pedido con id: {}", pedidoId);
        try {
            return pedidoWebClient.get()
                    .uri("/pedidos/{id}", pedidoId)
                    .retrieve()
                    .bodyToMono(PedidoClientDTO.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            log.error("Pedido con id {} no encontrado en el microservicio de Pedido", pedidoId);
            throw new IllegalArgumentException("El pedido con id " + pedidoId + " no existe.");
        } catch (Exception e) {
            log.error("Error al comunicarse con el microservicio de Pedido: {}", e.getMessage());
            throw new IllegalArgumentException("No se pudo verificar el pedido. Intenta nuevamente.");
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
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado con id: " + id));
        return toDTO(pago);
    }

    public PagoResponseDTO makePago(PagoRequestDTO dto) {
        log.info("Se inicia la creación de pago para pedidoId: {}", dto.getPedidoId());

        // ── Regla 1: El método de pago debe ser uno de los permitidos ─────────
        if (!METODOS_PERMITIDOS.contains(dto.getMetodoPago())) {
            throw new IllegalArgumentException(
                    "Método de pago inválido. Los métodos permitidos son: " + String.join(", ", METODOS_PERMITIDOS)
            );
        }

        // ── Regla 2: No se puede procesar un pago para un pedido ya pagado ────
        boolean yaExistePago = pagoRepository.findAll().stream()
                .anyMatch(p -> p.getPedidoId() != null && p.getPedidoId().equals(dto.getPedidoId()));
        if (yaExistePago) {
            log.warn("Intento de pagar dos veces el pedido con id: {}", dto.getPedidoId());
            throw new IllegalArgumentException("El pedido con id " + dto.getPedidoId() + " ya tiene un pago registrado.");
        }

        // ── Interacción: verificar que el pedido existe ───────────────────────
        PedidoClientDTO pedido = obtenerPedido(dto.getPedidoId());
        log.info("Pedido verificado: producto '{}' por ${}. Procediendo con el pago.", pedido.getNomProducto(), pedido.getPrecio());

        // ── Regla 3: El precio del pedido debe ser mayor a 0 ─────────────────
        if (pedido.getPrecio() <= 0) {
            throw new IllegalArgumentException("No se puede procesar un pago para un pedido con precio inválido.");
        }

        String comprobante = "COMP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Pago pago = new Pago();
        pago.setMetodoPago(dto.getMetodoPago());
        pago.setComprobante(comprobante);
        pago.setFecha(new Date());
        pago.setPedidoId(dto.getPedidoId());
        pago = pagoRepository.save(pago);

        log.info("Pago creado exitosamente con comprobante: {} para pedido '{}'", comprobante, pedido.getNomProducto());
        return toDTO(pago);
    }

    public void deletePago(long id) {
        log.info("Se elimina pago con id: {}", id);
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado con id: " + id));
        pagoRepository.delete(pago);
        log.info("Pago con id: {} eliminado exitosamente", id);
    }
}
