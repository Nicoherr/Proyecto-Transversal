package com.marketplace.valoracion.Service;

import com.marketplace.valoracion.DTO.ValoracionRequestDTO;
import com.marketplace.valoracion.DTO.ValoracionResponseDTO;
import com.marketplace.valoracion.model.Valoracion;
import com.marketplace.valoracion.repository.ValoracionRepository;
import com.marketplace.valoracion.service.ValoracionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ValoracionServiceTest {

    @InjectMocks
    private ValoracionService valoracionService;

    @Mock
    private ValoracionRepository valoracionRepository;

    @Mock
    private WebClient productoWebClient;

    // ─── Tests CRUD ───────────────────────────────────────────────────────────

    @Test
    public void testFindAll() {
        when(valoracionRepository.findAll()).thenReturn(List.of(crearValoracion()));

        List<ValoracionResponseDTO> resultado = valoracionService.findAllValoraciones();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(5, resultado.get(0).getNumEstrella());
    }

    @Test
    public void testFindById() {
        when(valoracionRepository.findById(1L)).thenReturn(Optional.of(crearValoracion()));

        ValoracionResponseDTO resultado = valoracionService.findValoracionById(1L);

        assertNotNull(resultado);
        assertEquals(5, resultado.getNumEstrella());
        assertEquals(1L, resultado.getProductoId());
    }

    @Test
    public void testFindById_NotFound() {
        when(valoracionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                valoracionService.findValoracionById(99L));
    }

    @Test
    public void testDeleteValoracion() {
        Valoracion valoracion = crearValoracion();
        when(valoracionRepository.findById(1L)).thenReturn(Optional.of(valoracion));
        doNothing().when(valoracionRepository).delete(valoracion);

        valoracionService.deleteValoracion(1L);

        verify(valoracionRepository, times(1)).delete(valoracion);
    }

    // ─── Tests reglas de negocio ──────────────────────────────────────────────

    @Test
    public void testMakeValoracion_EstrellaMinima() {
        ValoracionRequestDTO dto = crearRequest();
        dto.setNumEstrella(0);

        assertThrows(IllegalArgumentException.class, () ->
                valoracionService.makeValoracion(dto));
    }

    @Test
    public void testMakeValoracion_EstrellaMaxima() {
        ValoracionRequestDTO dto = crearRequest();
        dto.setNumEstrella(6);

        assertThrows(IllegalArgumentException.class, () ->
                valoracionService.makeValoracion(dto));
    }

    @Test
    public void testMakeValoracion_RecomendacionCorta() {
        ValoracionRequestDTO dto = crearRequest();
        dto.setRecomendacion("Corto");

        assertThrows(IllegalArgumentException.class, () ->
                valoracionService.makeValoracion(dto));
    }

    @Test
    public void testMakeValoracion_SugerenciaCorta() {
        ValoracionRequestDTO dto = crearRequest();
        dto.setSugerencia("Corta");

        assertThrows(IllegalArgumentException.class, () ->
                valoracionService.makeValoracion(dto));
    }

    @Test
    public void testMakeValoracion_Duplicado() {
        ValoracionRequestDTO dto = crearRequest();
        when(valoracionRepository.existsByProductoId(dto.getProductoId())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
                valoracionService.makeValoracion(dto));
    }

    // ─── Helpers ──────────────────────────────────────────────────────────────

    private Valoracion crearValoracion() {
        Valoracion v = new Valoracion();
        v.setId(1L);
        v.setProductoId(1L);
        v.setNumEstrella(5);
        v.setRecomendacion("Excelente producto, muy buena calidad");
        v.setSugerencia("Podrían mejorar el empaque del producto");
        return v;
    }

    private ValoracionRequestDTO crearRequest() {
        ValoracionRequestDTO dto = new ValoracionRequestDTO();
        dto.setProductoId(1L);
        dto.setNumEstrella(5);
        dto.setRecomendacion("Excelente producto, muy buena calidad");
        dto.setSugerencia("Podrían mejorar el empaque del producto");
        return dto;
    }
}