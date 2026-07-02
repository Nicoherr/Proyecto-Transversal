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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReporteServiceTest {

    @InjectMocks
    private ReporteService reporteService;

    @Mock
    private ReporteRepository reporteRepository;

    @Mock
    private WebClient pagoWebClient;

    // ─── Tests CRUD ───────────────────────────────────────────────────────────

    @Test
    public void testFindAll() {
        when(reporteRepository.findAll()).thenReturn(List.of(crearReporte()));

        List<ReporteResponseDTO> resultado = reporteService.findAllReportes();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Ventas mensuales", resultado.get(0).getTipo());
    }

    @Test
    public void testFindById() {
        when(reporteRepository.findById(1L)).thenReturn(Optional.of(crearReporte()));

        ReporteResponseDTO resultado = reporteService.findReportesById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getPagoId());
        assertTrue(resultado.getEstado());
    }

    @Test
    public void testFindById_NotFound() {
        when(reporteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                reporteService.findReportesById(99L));
    }

    @Test
    public void testDeleteReporte() {
        Reporte reporte = crearReporte();
        when(reporteRepository.findById(1L)).thenReturn(Optional.of(reporte));
        doNothing().when(reporteRepository).delete(reporte);

        reporteService.deleteReporte(1L);

        verify(reporteRepository, times(1)).delete(reporte);
    }

    // ─── Tests reglas de negocio ──────────────────────────────────────────────

    @Test
    public void testMakeReporte_TipoInvalido() {
        ReporteRequestDTO dto = crearRequest();
        dto.setTipo("Tipo inválido");

        assertThrows(IllegalArgumentException.class, () ->
                reporteService.makeReporte(dto));
    }

    @Test
    public void testMakeReporte_DescripcionCorta() {
        ReporteRequestDTO dto = crearRequest();
        dto.setDescripcion("Corta");

        assertThrows(IllegalArgumentException.class, () ->
                reporteService.makeReporte(dto));
    }

    @Test
    public void testMakeReporte_Duplicado() {
        ReporteRequestDTO dto = crearRequest();
        when(reporteRepository.existsByTipoAndEstado(dto.getTipo(), true)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
                reporteService.makeReporte(dto));
    }

    // ─── Helpers ──────────────────────────────────────────────────────────────

    private Reporte crearReporte() {
        Reporte r = new Reporte();
        r.setId(1L);
        r.setPagoId(1L);
        r.setTipo("Ventas mensuales");
        r.setDescripcion("Reporte de ventas del mes de junio 2026");
        r.setFecha(new Date());
        r.setEstado(true);
        return r;
    }

    private ReporteRequestDTO crearRequest() {
        ReporteRequestDTO dto = new ReporteRequestDTO();
        dto.setPagoId(1L);
        dto.setTipo("Ventas mensuales");
        dto.setDescripcion("Reporte de ventas del mes de junio 2026");
        return dto;
    }
}
