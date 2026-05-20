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
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PagoService {

    private final PagoRepository pagoRepository;
    private final WebClient pedidoWebClient; // Inyectamos el WebClient configurado

    private PagoResponseDTO makeToPagoResponseDTO(Pago pago) {
        return new PagoResponseDTO(pago.getId(), pago.getMetodoPago(), pago.getComprobante(), pago.getFecha());
    }

    // Método que consulta el microservicio de Pedido para validar que existe
    private PedidoClientDTO obtenerPedido(Long pedidoId) {
        log.info("Consultando microservicio Pedido para verificar pedido con id: {}", pedidoId);
        try {
            return pedidoWebClient.get()
                    .uri("/pedidos/{id}", pedidoId) // GET http://localhost:8086/pedidos/{id}
                    .retrieve()
                    .bodyToMono(PedidoClientDTO.class)
                    .block(); // block() convierte la llamada reactiva a síncrona
        } catch (WebClientResponseException.NotFound e) {
            log.error("Pedido con id {} no encontrado en el microservicio de Pedido", pedidoId);
            throw new IllegalArgumentException("El pedido con id " + pedidoId + " no existe");
        } catch (Exception e) {
            log.error("Error al comunicarse con el microservicio de Pedido: {}", e.getMessage());
            throw new IllegalArgumentException("No se pudo verificar el pedido. Intenta nuevamente.");
        }
    }

    public List<PagoResponseDTO> findAllPagos() {
        log.info("Se listan todos los pagos");
        return pagoRepository.findAll().stream()
                .map(this::makeToPagoResponseDTO)
                .collect(Collectors.toList());
    }

    public PagoResponseDTO findPagosById(long id) {
        log.info("Se busca pago con id: {}", id);
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado con id: " + id));
        return makeToPagoResponseDTO(pago);
    }

    public PagoResponseDTO makePago(PagoRequestDTO newPago) {
        log.info("Se inicia la creación de pago para pedidoId: {}", newPago.getPedidoId());

        // Comunicación con microservicio Pedido: verifica que el pedido existe
        PedidoClientDTO pedido = obtenerPedido(newPago.getPedidoId());
        log.info("Pedido verificado: producto '{}' por precio ${}. Procediendo con el pago.", pedido.getNomProducto(), pedido.getPrecio());

        String comprobante = "COMP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Pago pago = new Pago();
        pago.setMetodoPago(newPago.getMetodoPago());
        pago.setComprobante(comprobante);
        pago.setFecha(new Date());
        pago = pagoRepository.save(pago);

        log.info("Pago creado exitosamente con comprobante: {}", comprobante);
        return makeToPagoResponseDTO(pago);
    }

    public void deletePago(long id) {
        log.info("Se elimina pago con id: {}", id);
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado con id: " + id));
        pagoRepository.delete(pago);
    }
}