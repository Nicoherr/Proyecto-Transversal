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

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ValoracionServiceTest {

    @InjectMocks
    private ValoracionService valoracionService;

    @Mock
    private ValoracionRepository valoracionRepository;

    @Mock
    private WebClient productoWebClient;

    @Test
    public void testFindAll() {
        Valoracion valoracion = crearValoracion();
        when(valoracionRepository.findAll()).thenReturn(List.of(valoracion));

        List<ValoracionResponseDTO> resultado = valoracionService.findAllValoraciones();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(5, resultado.get(0).getNumEstrella());
    }

    @Test
    public void testFindById() {
        long id = 1L;
        Valoracion valoracion = crearValoracion();
        when(valoracionRepository.findById(id)).thenReturn(Optional.of(valoracion));

        ValoracionResponseDTO resultado = valoracionService.findValoracionById(id);

        assertNotNull(resultado);
        assertEquals(5, resultado.getNumEstrella());
    }

    @Test
    public void testFindById_NotFound() {
        long id = 99L;
        when(valoracionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            valoracionService.findValoracionById(id);
        });
    }

    @Test
    public void testDeleteValoracion() {
        long id = 1L;
        Valoracion valoracion = crearValoracion();
        when(valoracionRepository.findById(id)).thenReturn(Optional.of(valoracion));
        doNothing().when(valoracionRepository).delete(valoracion);

        valoracionService.deleteValoracion(id);

        verify(valoracionRepository, times(1)).delete(valoracion);
    }

    @Test
    public void testMakeValoracion_EstrellaInvalida() {
        ValoracionRequestDTO dto = new ValoracionRequestDTO();
        dto.setProductoId(1L);
        dto.setNumEstrella(6);
        dto.setRecomendacion("Excelente producto muy bueno");

        assertThrows(IllegalArgumentException.class, () -> {
            valoracionService.makeValoracion(dto);
        });
    }

    @Test
    public void testMakeValoracion_RecomendacionCorta() {
        ValoracionRequestDTO dto = new ValoracionRequestDTO();
        dto.setProductoId(1L);
        dto.setNumEstrella(4);
        dto.setRecomendacion("Corto");

        assertThrows(IllegalArgumentException.class, () -> {
            valoracionService.makeValoracion(dto);
        });
    }

    private Valoracion crearValoracion() {
        Valoracion valoracion = new Valoracion();
        valoracion.setId(1L);
        valoracion.setNumEstrella(5);
        valoracion.setRecomendacion("Excelente producto, muy buena calidad");
        return valoracion;
    }
}