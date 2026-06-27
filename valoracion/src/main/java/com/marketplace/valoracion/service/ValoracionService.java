package com.marketplace.valoracion.service;

import com.marketplace.valoracion.DTO.ProductoClientDTO;
import com.marketplace.valoracion.DTO.ValoracionRequestDTO;
import com.marketplace.valoracion.DTO.ValoracionResponseDTO;
import com.marketplace.valoracion.model.Valoracion;
import com.marketplace.valoracion.repository.ValoracionRepository;
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
public class ValoracionService {

    private final ValoracionRepository valoracionRepository;
    private final WebClient productoWebClient;

    // ─── Mapper ───────────────────────────────────────────────────────────────
    private ValoracionResponseDTO toDTO(Valoracion v) {
        return new ValoracionResponseDTO(
                v.getId(),
                v.getProductoId(),
                v.getNumEstrella(),
                v.getRecomendacion(),
                v.getSugerencia()
        );
    }

    private Valoracion obtenerOFallar(long id) {
        return valoracionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Valoración no encontrada con id: " + id));
    }

    // ─── Comunicación con microservicio Producto ───────────────────────────────
    private ProductoClientDTO obtenerProducto(Long productoId) {
        log.info("Consultando microservicio Producto para id: {}", productoId);
        try {
            return productoWebClient.get()
                    .uri("/api/producto/{id}", productoId)
                    .retrieve()
                    .bodyToMono(ProductoClientDTO.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            log.error("Producto con id {} no encontrado", productoId);
            throw new IllegalArgumentException(
                    "No se puede valorar: el producto con id " + productoId + " no existe."
            );
        } catch (Exception e) {
            log.error("Microservicio Producto no disponible: {}", e.getMessage());
            throw new IllegalArgumentException(
                    "El servicio de productos no está disponible en este momento. Intenta más tarde."
            );
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
        log.info("Creando valoración para productoId: {}", dto.getProductoId());

        // ── Regla 1: No puede haber dos valoraciones para el mismo producto ──
        if (valoracionRepository.existsByProductoId(dto.getProductoId())) {
            throw new IllegalArgumentException(
                    "Ya existe una valoración para el producto con id: " + dto.getProductoId()
            );
        }

        // ── Regla 2: Las estrellas deben estar entre 1 y 5 ──────────────────
        if (dto.getNumEstrella() < 1 || dto.getNumEstrella() > 5) {
            throw new IllegalArgumentException("El número de estrellas debe estar entre 1 y 5.");
        }

        // ── Regla 3: La recomendación debe tener al menos 10 caracteres ──────
        if (dto.getRecomendacion().trim().length() < 10) {
            throw new IllegalArgumentException("La recomendación debe tener al menos 10 caracteres.");
        }

        // ── Regla 4: La sugerencia debe tener al menos 10 caracteres ─────────
        if (dto.getSugerencia().trim().length() < 10) {
            throw new IllegalArgumentException("La sugerencia debe tener al menos 10 caracteres.");
        }

        // ── Verifica que el producto existe y está activo ─────────────────────
        ProductoClientDTO producto = obtenerProducto(dto.getProductoId());
        if (!producto.getActivo()) {
            throw new IllegalArgumentException("No se puede valorar un producto que no está activo.");
        }

        Valoracion valoracion = new Valoracion();
        valoracion.setProductoId(dto.getProductoId());
        valoracion.setNumEstrella(dto.getNumEstrella());
        valoracion.setRecomendacion(dto.getRecomendacion().trim());
        valoracion.setSugerencia(dto.getSugerencia().trim());
        valoracion = valoracionRepository.save(valoracion);

        log.info("Valoración creada con id: {}", valoracion.getId());
        return toDTO(valoracion);
    }

    public ValoracionResponseDTO updateValoracion(long id, ValoracionRequestDTO dto) {
        log.info("Actualizando valoración con id: {}", id);

        if (dto.getNumEstrella() < 1 || dto.getNumEstrella() > 5) {
            throw new IllegalArgumentException("El número de estrellas debe estar entre 1 y 5.");
        }
        if (dto.getRecomendacion().trim().length() < 10) {
            throw new IllegalArgumentException("La recomendación debe tener al menos 10 caracteres.");
        }
        if (dto.getSugerencia().trim().length() < 10) {
            throw new IllegalArgumentException("La sugerencia debe tener al menos 10 caracteres.");
        }

        Valoracion valoracion = obtenerOFallar(id);
        valoracion.setNumEstrella(dto.getNumEstrella());
        valoracion.setRecomendacion(dto.getRecomendacion().trim());
        valoracion.setSugerencia(dto.getSugerencia().trim());
        valoracion = valoracionRepository.save(valoracion);

        log.info("Valoración con id: {} actualizada exitosamente", id);
        return toDTO(valoracion);
    }

    public void deleteValoracion(long id) {
        log.info("Eliminando valoración con id: {}", id);
        valoracionRepository.delete(obtenerOFallar(id));
        log.info("Valoración con id: {} eliminada exitosamente", id);
    }
}