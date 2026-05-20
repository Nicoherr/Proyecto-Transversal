package com.marketplace.reporte.repository;

import com.marketplace.reporte.model.DetalleReporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleReporteRepository extends JpaRepository<DetalleReporte, Long> {
    // Busca todos los detalles de un reporte específico por su ID
    List<DetalleReporte> findByReporteId(Long reporteId);
}
