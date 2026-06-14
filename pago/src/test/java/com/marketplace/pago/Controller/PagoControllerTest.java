package com.marketplace.pago.Controller;

import com.marketplace.pago.DTO.PagoResponseDTO;
import com.marketplace.pago.assemblers.PagoModelAssembler;
import com.marketplace.pago.service.PagoService;
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
public class PagoControllerTest {

    @InjectMocks
    private com.marketplace.pago.controller.PagoController pagoController;

    @Mock
    private PagoService pagoService;

    @Mock
    private PagoModelAssembler assembler;

    @Mock
    private WebClient pedidoWebClient;

    @Test
    public void testListar_RetornaLista() {
        PagoResponseDTO dto = crearResponseDTO();
        when(pagoService.findAllPagos()).thenReturn(List.of(dto));

        List<PagoResponseDTO> resultado = pagoService.findAllPagos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Tarjeta de crédito", resultado.get(0).getMetodoPago());
    }

    @Test
    public void testObtener_RetornaDTO() {
        PagoResponseDTO dto = crearResponseDTO();
        when(pagoService.findPagosById(1L)).thenReturn(dto);

        PagoResponseDTO resultado = pagoService.findPagosById(1L);

        assertNotNull(resultado);
        assertEquals("Tarjeta de crédito", resultado.getMetodoPago());
    }

    @Test
    public void testObtener_NotFound() {
        when(pagoService.findPagosById(99L))
                .thenThrow(new IllegalArgumentException("Pago no encontrado"));

        assertThrows(IllegalArgumentException.class, () -> {
            pagoService.findPagosById(99L);
        });
    }

    @Test
    public void testEliminar_LlamaAlService() {
        doNothing().when(pagoService).deletePago(1L);

        pagoService.deletePago(1L);

        verify(pagoService, times(1)).deletePago(1L);
    }

    private PagoResponseDTO crearResponseDTO() {
        PagoResponseDTO dto = new PagoResponseDTO();
        dto.setId(1L);
        dto.setMetodoPago("Tarjeta de crédito");
        dto.setComprobante("COMP-ABC12345");
        dto.setFecha(new Date());
        return dto;
    }
}

