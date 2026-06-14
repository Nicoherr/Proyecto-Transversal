package com.marketplace.reporte.Service;

import com.marketplace.reporte.DTO.ReporteRequestDTO;
import com.marketplace.reporte.DTO.ReporteResponseDTO;
import com.marketplace.reporte.model.Reporte;
import com.marketplace.reporte.repository.ReporteRepository;
import com.marketplace.reporte.service.ReporteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ReporteServiceTest {

    @InjectMocks
    private ReporteService reporteService;

    @Mock
    private ReporteRepository reporteRepository;

    @Mock
    private WebClient pagoWebClient;

    @Test
    public void testFindAll() {
        Reporte reporte = crearReporte();
        when(reporteRepository.findAll()).thenReturn(List.of(reporte));

        List<ReporteResponseDTO> resultado = reporteService.findAllReportes();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Ventas mensuales", resultado.get(0).getTipo());
    }

    @Test
    public void testFindById() {
        long id = 1L;
        Reporte reporte = crearReporte();
        when(reporteRepository.findById(id)).thenReturn(Optional.of(reporte));

        ReporteResponseDTO resultado = reporteService.findReportesById(id);

        assertNotNull(resultado);
        assertEquals("Ventas mensuales", resultado.getTipo());
    }

    @Test
    public void testFindById_NotFound() {
        long id = 99L;
        when(reporteRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            reporteService.findReportesById(id);
        });
    }

    @Test
    public void testDeleteReporte() {
        long id = 1L;
        Reporte reporte = crearReporte();
        when(reporteRepository.findById(id)).thenReturn(Optional.of(reporte));
        doNothing().when(reporteRepository).delete(reporte);

        reporteService.deleteReporte(id);

        verify(reporteRepository, times(1)).delete(reporte);
    }

    @Test
    public void testMakeReporte_TipoInvalido() {
        ReporteRequestDTO dto = new ReporteRequestDTO();
        dto.setPagoId(1L);
        dto.setTipo("Tipo inválido");
        dto.setDescripcion("Descripción válida del reporte");

        assertThrows(IllegalArgumentException.class, () -> {
            reporteService.makeReporte(dto);
        });
    }

    @Test
    public void testMakeReporte_DescripcionCorta() {
        ReporteRequestDTO dto = new ReporteRequestDTO();
        dto.setPagoId(1L);
        dto.setTipo("Ventas mensuales");
        dto.setDescripcion("Corto");

        assertThrows(IllegalArgumentException.class, () -> {
            reporteService.makeReporte(dto);
        });
    }

    private Reporte crearReporte() {
        Reporte reporte = new Reporte();
        reporte.setId(1L);
        reporte.setTipo("Ventas mensuales");
        reporte.setDescripcion("Resumen de ventas del mes de junio");
        reporte.setFecha(new Date());
        reporte.setEstado(true);
        return reporte;
    }
}
