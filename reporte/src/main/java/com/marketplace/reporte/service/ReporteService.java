package com.marketplace.reporte.service;

import com.marketplace.reporte.model.Reporte;
import com.marketplace.reporte.repository.ReporteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    //Listar
    public List<Reporte> getReporte(){
        return reporteRepository.findAll();
    }

    //Crear
    public ReporteDTO createReporte(ReporteNewDTO newReporteDTO){
        Reporte reporte = new Reporte(0, newReporteDTO.getTipo(), newReporteDTO.getDescripcion(), new Date(), true);
        reporte = reporteRepository.save(reporte);
        ReporteDTO reporteDTO = new ReporteDTO(reporte.getId(), reporte.getTipo(), reporte.getdescripcion());
        return reporteDTO;
    }

    //Eliminar
    public void deleteReporte(long id){
        Reporte reporte = reporteRepository.findById(id).get();
        reporteRepository.delete(reporte);

    }
}
