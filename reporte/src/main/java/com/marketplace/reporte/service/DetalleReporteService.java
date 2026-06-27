package com.marketplace.reporte.service;

import com.marketplace.reporte.DTO.DetalleReporteRequestDTO;
import com.marketplace.reporte.DTO.DetalleReporteResponseDTO;
import com.marketplace.reporte.model.DetalleReporte;
import com.marketplace.reporte.model.Reporte;
import com.marketplace.reporte.repository.DetalleReporteRepository;
import com.marketplace.reporte.repository.ReporteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DetalleReporteService {

    private final DetalleReporteRepository detalleReporteRepository;
    private final ReporteRepository reporteRepository;

    // ─── Mapper ───────────────────────────────────────────────────────────────
    private DetalleReporteResponseDTO toDTO(DetalleReporte detalle) {
        return new DetalleReporteResponseDTO(
                detalle.getId(),
                detalle.getObservacion(),
                detalle.getValor(),
                detalle.getReporte().getId(),
                detalle.getReporte().getTipo()
        );
    }

    private DetalleReporte obtenerOFallar(Long id) {
        return detalleReporteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Detalle no encontrado con id: " + id));
    }

    private Reporte obtenerReporteOFallar(Long reporteId) {
        return reporteRepository.findById(reporteId)
                .orElseThrow(() -> new IllegalArgumentException("Reporte no encontrado con id: " + reporteId));
    }

    // ─── CRUD ─────────────────────────────────────────────────────────────────
    public List<DetalleReporteResponseDTO> findByReporteId(Long reporteId) {
        log.info("Se listan los detalles del reporte con id: {}", reporteId);

        // Verifica que el reporte padre existe
        obtenerReporteOFallar(reporteId);

        return detalleReporteRepository.findByReporteId(reporteId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public DetalleReporteResponseDTO makeDetalle(DetalleReporteRequestDTO dto) {
        log.info("Creando detalle para reporte id: {}", dto.getReporteId());

        // ── Regla 1: La observación debe tener al menos 10 caracteres ────────
        if (dto.getObservacion().trim().length() < 10) {
            throw new IllegalArgumentException(
                    "La observación debe tener al menos 10 caracteres."
            );
        }

        // ── Regla 2: El valor debe ser mayor a 0 ─────────────────────────────
        if (dto.getValor() <= 0) {
            throw new IllegalArgumentException("El valor del detalle debe ser mayor a 0.");
        }

        // ── Verifica que el reporte padre existe y está activo ────────────────
        Reporte reporte = obtenerReporteOFallar(dto.getReporteId());

        if (!Boolean.TRUE.equals(reporte.getEstado())) {
            throw new IllegalArgumentException(
                    "No se puede agregar detalles a un reporte inactivo (id: " + dto.getReporteId() + ")."
            );
        }

        DetalleReporte detalle = new DetalleReporte();
        detalle.setObservacion(dto.getObservacion().trim());
        detalle.setValor(dto.getValor());
        detalle.setReporte(reporte);
        detalle = detalleReporteRepository.save(detalle);

        log.info("Detalle creado con id: {} para reporte: {}", detalle.getId(), reporte.getTipo());
        return toDTO(detalle);
    }

    public DetalleReporteResponseDTO updateDetalle(Long id, DetalleReporteRequestDTO dto) {
        log.info("Actualizando detalle con id: {}", id);

        // ── Regla 1: La observación debe tener al menos 10 caracteres ────────
        if (dto.getObservacion().trim().length() < 10) {
            throw new IllegalArgumentException(
                    "La observación debe tener al menos 10 caracteres."
            );
        }

        // ── Regla 2: El valor debe ser mayor a 0 ─────────────────────────────
        if (dto.getValor() <= 0) {
            throw new IllegalArgumentException("El valor del detalle debe ser mayor a 0.");
        }

        DetalleReporte detalle = obtenerOFallar(id);
        detalle.setObservacion(dto.getObservacion().trim());
        detalle.setValor(dto.getValor());
        detalle = detalleReporteRepository.save(detalle);

        log.info("Detalle con id: {} actualizado exitosamente", id);
        return toDTO(detalle);
    }

    public void deleteDetalle(Long id) {
        log.info("Eliminando detalle con id: {}", id);
        detalleReporteRepository.delete(obtenerOFallar(id));
        log.info("Detalle con id: {} eliminado exitosamente", id);
    }
}