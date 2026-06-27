package com.marketplace.reporte.repository;

import com.marketplace.reporte.model.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {

    // Chequeo de duplicado eficiente (sin cargar todos los reportes)
    boolean existsByTipoAndEstado(String tipo, Boolean estado);

    // Buscar reportes de un pago específico
    List<Reporte> findByPagoId(Long pagoId);

    // Buscar por tipo
    List<Reporte> findByTipo(String tipo);

    // Buscar por estado (activos o inactivos)
    List<Reporte> findByEstado(Boolean estado);
}