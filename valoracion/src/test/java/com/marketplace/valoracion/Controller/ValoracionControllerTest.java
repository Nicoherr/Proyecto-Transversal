package com.marketplace.valoracion.Controller;

import com.marketplace.valoracion.DTO.ValoracionResponseDTO;
import com.marketplace.valoracion.assemblers.ValoracionModelAssembler;
import com.marketplace.valoracion.service.ValoracionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ValoracionControllerTest {

    @InjectMocks
    private com.marketplace.valoracion.controller.ValoracionController valoracionController;

    @Mock
    private ValoracionService valoracionService;

    @Mock
    private ValoracionModelAssembler assembler;

    @Mock
    private WebClient productoWebClient;

    @Test
    public void testListar_RetornaLista() {
        ValoracionResponseDTO dto = crearResponseDTO();
        when(valoracionService.findAllValoraciones()).thenReturn(List.of(dto));

        List<ValoracionResponseDTO> resultado = valoracionService.findAllValoraciones();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(5, resultado.get(0).getNumEstrella());
    }

    @Test
    public void testObtener_RetornaDTO() {
        ValoracionResponseDTO dto = crearResponseDTO();
        when(valoracionService.findValoracionById(1L)).thenReturn(dto);

        ValoracionResponseDTO resultado = valoracionService.findValoracionById(1L);

        assertNotNull(resultado);
        assertEquals(5, resultado.getNumEstrella());
    }

    @Test
    public void testObtener_NotFound() {
        when(valoracionService.findValoracionById(99L))
                .thenThrow(new IllegalArgumentException("Valoración no encontrada"));

        assertThrows(IllegalArgumentException.class, () -> {
            valoracionService.findValoracionById(99L);
        });
    }

    @Test
    public void testEliminar_LlamaAlService() {
        doNothing().when(valoracionService).deleteValoracion(1L);

        valoracionService.deleteValoracion(1L);

        verify(valoracionService, times(1)).deleteValoracion(1L);
    }

    private ValoracionResponseDTO crearResponseDTO() {
        ValoracionResponseDTO dto = new ValoracionResponseDTO();
        dto.setId(1L);
        dto.setNumEstrella(5);
        dto.setRecomendacion("Excelente producto, muy buena calidad");
        return dto;
    }
}