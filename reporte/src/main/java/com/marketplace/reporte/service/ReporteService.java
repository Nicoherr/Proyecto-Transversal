package com.marketplace.reporte.service;

import com.marketplace.reporte.DTO.PagoClientDTO;
import com.marketplace.reporte.DTO.ReporteRequestDTO;
import com.marketplace.reporte.DTO.ReporteResponseDTO;
import com.marketplace.reporte.model.Reporte;
import com.marketplace.reporte.repository.ReporteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReporteService {

    private final ReporteRepository reporteRepository;
    private final WebClient pagoWebClient;

    private static final Set<String> TIPOS_VALIDOS = Set.of(
            "Ventas mensuales", "Ventas anuales", "Pagos pendientes",
            "Productos más vendidos", "Clientes frecuentes"
    );

    // ─── Mapper ───────────────────────────────────────────────────────────────
    private ReporteResponseDTO toDTO(Reporte r) {
        return new ReporteResponseDTO(
                r.getId(),
                r.getPagoId(),
                r.getTipo(),
                r.getDescripcion(),
                r.getFecha(),
                r.getEstado()
        );
    }

    private Reporte obtenerOFallar(long id) {
        return reporteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reporte no encontrado con id: " + id));
    }

    // ─── Comunicación con microservicio Pago ──────────────────────────────────
    private PagoClientDTO obtenerPago(Long pagoId) {
        log.info("Consultando microservicio Pago para id: {}", pagoId);
        try {
            return pagoWebClient.get()
                    .uri("/pagos/{id}", pagoId)
                    .retrieve()
                    .bodyToMono(PagoClientDTO.class)
                    .block();
        } catch (WebClientResponseException.NotFound e) {
            log.error("Pago con id {} no encontrado", pagoId);
            throw new IllegalArgumentException(
                    "No se puede generar el reporte: el pago con id " + pagoId + " no existe."
            );
        } catch (Exception e) {
            log.error("Microservicio Pago no disponible: {}", e.getMessage());
            throw new IllegalArgumentException(
                    "El servicio de pagos no está disponible en este momento. Intenta más tarde."
            );
        }
    }

    // ─── CRUD ─────────────────────────────────────────────────────────────────
    public List<ReporteResponseDTO> findAllReportes() {
        log.info("Se listan todos los reportes");
        return reporteRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ReporteResponseDTO findReportesById(long id) {
        log.info("Se busca reporte con id: {}", id);
        return toDTO(obtenerOFallar(id));
    }

    public ReporteResponseDTO makeReporte(ReporteRequestDTO dto) {
        log.info("Creando reporte tipo: '{}' para pagoId: {}", dto.getTipo(), dto.getPagoId());

        // ── Regla 1: El tipo debe ser uno de los válidos ──────────────────────
        if (!TIPOS_VALIDOS.contains(dto.getTipo())) {
            throw new IllegalArgumentException(
                    "Tipo de reporte inválido. Los tipos permitidos son: "
                            + String.join(", ", TIPOS_VALIDOS)
            );
        }

        // ── Regla 2: La descripción debe tener al menos 10 caracteres ────────
        if (dto.getDescripcion().trim().length() < 10) {
            throw new IllegalArgumentException(
                    "La descripción del reporte debe tener al menos 10 caracteres."
            );
        }

        // ── Regla 3: No puede haber un reporte activo del mismo tipo ─────────
        // Usa existsByTipoAndEstado en vez de findAll().stream() para ser eficiente
        if (reporteRepository.existsByTipoAndEstado(dto.getTipo(), true)) {
            log.warn("Intento de crear reporte duplicado de tipo: '{}'", dto.getTipo());
            throw new IllegalArgumentException(
                    "Ya existe un reporte activo de tipo '" + dto.getTipo() + "'."
            );
        }

        // ── Verifica que el pago existe ───────────────────────────────────────
        PagoClientDTO pago = obtenerPago(dto.getPagoId());
        log.info("Pago verificado: comprobante '{}'. Creando reporte.", pago.getComprobante());

        String descripcionFinal = dto.getDescripcion().trim()
                + " [Comprobante: " + pago.getComprobante()
                + " | Método: " + pago.getMetodoPago() + "]";

        Reporte reporte = new Reporte();
        reporte.setPagoId(dto.getPagoId());
        reporte.setTipo(dto.getTipo());
        reporte.setDescripcion(descripcionFinal);
        reporte.setFecha(new Date());
        reporte.setEstado(true);
        reporte = reporteRepository.save(reporte);

        log.info("Reporte tipo '{}' creado con id: {}", dto.getTipo(), reporte.getId());
        return toDTO(reporte);
    }

    public void deleteReporte(long id) {
        log.info("Eliminando reporte con id: {}", id);
        reporteRepository.delete(obtenerOFallar(id));
        log.info("Reporte con id: {} eliminado exitosamente", id);
    }
}
