package com.marketplace.valoracion.service;

import com.marketplace.valoracion.DTO.ProductoClientDTO;
import com.marketplace.valoracion.DTO.ValoracionRequestDTO;
import com.marketplace.valoracion.DTO.ValoracionResponseDTO;
import com.marketplace.valoracion.model.Valoracion;
import com.marketplace.valoracion.repository.ValoracionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ValoracionService {

    private final ValoracionRepository valoracionRepository;
    private final WebClient productoWebClient;

    // ─── Mapper ───────────────────────────────────────────────────────────────
    private ValoracionResponseDTO toDTO(Valoracion v) {
        return new ValoracionResponseDTO(v.getId(), v.getNumEstrella(), v.getRecomendacion());
    }

    private Valoracion obtenerOFallar(long id) {
        return valoracionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Valoración no encontrada con id: " + id));
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
            throw new IllegalArgumentException("No se puede valorar: el producto con id " + productoId + " no existe.");
        } catch (Exception e) {
            log.error("Error al comunicarse con el microservicio de Producto: {}", e.getMessage());
            throw new IllegalArgumentException("No se pudo verificar el producto. Intenta nuevamente.");
        }
    }

    // ─── CRUD ─────────────────────────────────────────────────────────────────
    public List<ValoracionResponseDTO> findAllValoraciones() {
        log.info("Se listan todas las valoraciones");
        return valoracionRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ValoracionResponseDTO findValoracionById(long id) {
        log.info("Se busca valoración con id: {}", id);
        return toDTO(obtenerOFallar(id));
    }

    public ValoracionResponseDTO makeValoracion(ValoracionRequestDTO dto) {
        log.info("Se inicia la creación de valoración para productoId: {}", dto.getProductoId());

        // ── Regla 1: Las estrellas deben estar entre 1 y 5 ──────────────────
        if (dto.getNumEstrella() < 1 || dto.getNumEstrella() > 5) {
            throw new IllegalArgumentException("El número de estrellas debe estar entre 1 y 5.");
        }

        // ── Regla 2: La recomendación debe tener al menos 10 caracteres ──────
        if (dto.getRecomendacion() == null || dto.getRecomendacion().trim().length() < 10) {
            throw new IllegalArgumentException("La recomendación debe tener al menos 10 caracteres.");
        }

        // ── Interacción: verificar que el producto existe y está activo ───────
        ProductoClientDTO producto = obtenerProducto(dto.getProductoId());

        if (!producto.getActivo()) {
            log.warn("Intento de valorar producto inactivo con id: {}", dto.getProductoId());
            throw new IllegalArgumentException("No se puede valorar un producto que no está activo.");
        }

        log.info("Producto '{}' verificado. Procediendo con la valoración.", producto.getNombre());

        Valoracion valoracion = new Valoracion();
        valoracion.setNumEstrella(dto.getNumEstrella());
        valoracion.setRecomendacion(dto.getRecomendacion().trim());
        valoracion = valoracionRepository.save(valoracion);

        log.info("Valoración creada exitosamente con id: {} para producto '{}'", valoracion.getId(), producto.getNombre());
        return toDTO(valoracion);
    }

    public ValoracionResponseDTO updateValoracion(long id, ValoracionRequestDTO dto) {
        log.info("Se actualiza valoración con id: {}", id);

        // ── Regla: Las estrellas deben estar entre 1 y 5 ────────────────────
        if (dto.getNumEstrella() < 1 || dto.getNumEstrella() > 5) {
            throw new IllegalArgumentException("El número de estrellas debe estar entre 1 y 5.");
        }

        // ── Regla: La recomendación debe tener al menos 10 caracteres ────────
        if (dto.getRecomendacion() == null || dto.getRecomendacion().trim().length() < 10) {
            throw new IllegalArgumentException("La recomendación debe tener al menos 10 caracteres.");
        }

        Valoracion valoracion = obtenerOFallar(id);
        valoracion.setNumEstrella(dto.getNumEstrella());
        valoracion.setRecomendacion(dto.getRecomendacion().trim());
        valoracion = valoracionRepository.save(valoracion);

        log.info("Valoración con id: {} actualizada exitosamente", id);
        return toDTO(valoracion);
    }

    public void deleteValoracion(long id) {
        log.info("Se elimina valoración con id: {}", id);
        valoracionRepository.delete(obtenerOFallar(id));
        log.info("Valoración con id: {} eliminada exitosamente", id);
    }
}
