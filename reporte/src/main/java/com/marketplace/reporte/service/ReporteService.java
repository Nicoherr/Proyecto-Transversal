package com.marketplace.reporte.service;

import com.marketplace.reporte.DTO.ReporteRequestDTO;
import com.marketplace.reporte.DTO.ReporteResponseDTO;
import com.marketplace.reporte.model.Reporte;
import com.marketplace.reporte.repository.ReporteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReporteService {

    @Autowired
    private final ReporteRepository reporteRepository;

    private ReporteResponseDTO makeToReporteResponseDTO(Reporte reporte){
        return new ReporteResponseDTO(reporte.getId(),reporte.getTipo(),reporte.getDescripcion(),reporte.getFecha(),reporte.getEstado());

        //Listar
        public List<ReporteResponseDTO> findAllReportes(){
            return reporteRepository.findAll().stream().map(this::makeToReporteResponseDTO).collect(Collectors.toList());
        }

        //Buscar
        public ReporteRequestDTO findReportesById(long id) {
            Reporte reporte = reporteRepository.findById(id).get();
            return new ReporteRequestDTO(reporte.getTipo(),reporte.getDescripcion());
        }

        //Crear
        public ReporteResponseDTO createReporte(ReporteRequestDTO newReporteDTO){
            Reporte reporte = new Reporte(0, newReporte.getTipo(), newReporte.getDescripcion(), newReporte.getFecha(), reporte.getEstado());
            reporte = reporteRepository.save(reporte);
            ReporteResponseDTO reporteDTO = new ReporteResponseDTO(valoracion.getId(), valoracion.getNumEstrella(), valoracion.getRecomendacion());
            return reporteDTO;
        }

        //Eliminar
        public void deleteReporte(long id) {
            Reporte reporte = reporteRepository.findById(id).get();
            reporteRepository.delete(reporte);
        }
}
