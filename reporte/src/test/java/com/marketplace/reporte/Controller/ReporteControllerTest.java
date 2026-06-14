package com.marketplace.reporte.Controller;

import com.marketplace.reporte.DTO.ReporteResponseDTO;
import com.marketplace.reporte.assemblers.ReporteModelAssembler;
import com.marketplace.reporte.service.ReporteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ReporteControllerTest {

    @InjectMocks
    private com.marketplace.reporte.controller.ReporteController reporteController;

    @Mock
    private ReporteService reporteService;

    @Mock
    private ReporteModelAssembler assembler;

    @Mock
    private WebClient pagoWebClient;

    @Test
    public void testListar_RetornaLista() {
        ReporteResponseDTO dto = crearResponseDTO();
        when(reporteService.findAllReportes()).thenReturn(List.of(dto));

        List<ReporteResponseDTO> resultado = reporteService.findAllReportes();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Ventas mensuales", resultado.get(0).getTipo());
    }

    @Test
    public void testObtener_RetornaDTO() {
        ReporteResponseDTO dto = crearResponseDTO();
        when(reporteService.findReportesById(1L)).thenReturn(dto);

        ReporteResponseDTO resultado = reporteService.findReportesById(1L);

        assertNotNull(resultado);
        assertEquals("Ventas mensuales", resultado.getTipo());
    }

    @Test
    public void testObtener_NotFound() {
        when(reporteService.findReportesById(99L))
                .thenThrow(new IllegalArgumentException("Reporte no encontrado"));

        assertThrows(IllegalArgumentException.class, () -> {
            reporteService.findReportesById(99L);
        });
    }

    @Test
    public void testEliminar_LlamaAlService() {
        doNothing().when(reporteService).deleteReporte(1L);

        reporteService.deleteReporte(1L);

        verify(reporteService, times(1)).deleteReporte(1L);
    }

    private ReporteResponseDTO crearResponseDTO() {
        ReporteResponseDTO dto = new ReporteResponseDTO();
        dto.setId(1L);
        dto.setTipo("Ventas mensuales");
        dto.setDescripcion("Resumen de ventas del mes de junio");
        dto.setFecha(new Date());
        dto.setEstado(true);
        return dto;
    }
}