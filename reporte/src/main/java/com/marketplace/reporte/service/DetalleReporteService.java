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

    // Convierte la entidad a ResponseDTO
    private DetalleReporteResponseDTO makeToResponseDTO(DetalleReporte detalle) {
        return new DetalleReporteResponseDTO(
                detalle.getId(),
                detalle.getObservacion(),
                detalle.getValor(),
                detalle.getReporte().getId(),   // ID del reporte padre (llave foránea)
                detalle.getReporte().getTipo()  // Tipo del reporte padre
        );
    }

    // Listar todos los detalles de un reporte específico
    public List<DetalleReporteResponseDTO> findByReporteId(Long reporteId) {
        log.info("Se listan los detalles del reporte con id: {}", reporteId);
        return detalleReporteRepository.findByReporteId(reporteId).stream()
                .map(this::makeToResponseDTO)
                .collect(Collectors.toList());
    }

    // Crear un detalle vinculado a un reporte existente
    public DetalleReporteResponseDTO makeDetalle(DetalleReporteRequestDTO dto) {
        log.info("Se crea detalle para reporte con id: {}", dto.getReporteId());

        // Verifica que el reporte existe antes de crear el detalle
        Reporte reporte = reporteRepository.findById(dto.getReporteId())
                .orElseThrow(() -> new IllegalArgumentException("Reporte no encontrado con id: " + dto.getReporteId()));

        // Crea el detalle y lo vincula al reporte mediante la relación @ManyToOne
        DetalleReporte detalle = new DetalleReporte();
        detalle.setObservacion(dto.getObservacion());
        detalle.setValor(dto.getValor());
        detalle.setReporte(reporte); // Asigna el objeto Reporte completo, no solo el ID

        detalle = detalleReporteRepository.save(detalle);
        log.info("Detalle creado con id: {} para reporte: {}", detalle.getId(), reporte.getTipo());
        return makeToResponseDTO(detalle);
    }

    // Eliminar un detalle
    public void deleteDetalle(Long id) {
        log.info("Se elimina detalle con id: {}", id);
        DetalleReporte detalle = detalleReporteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Detalle no encontrado con id: " + id));
        detalleReporteRepository.delete(detalle);
    }
}
