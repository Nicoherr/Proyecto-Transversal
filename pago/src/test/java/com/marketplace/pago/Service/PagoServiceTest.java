package com.marketplace.pago.Service;

import com.marketplace.pago.DTO.PagoRequestDTO;
import com.marketplace.pago.DTO.PagoResponseDTO;
import com.marketplace.pago.model.Pago;
import com.marketplace.pago.repository.PagoRepository;
import com.marketplace.pago.service.PagoService;
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
public class PagoServiceTest {

    @InjectMocks
    private PagoService pagoService;

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private WebClient pedidoWebClient;

    @Test
    public void testFindAll() {
        Pago pago = crearPago();
        when(pagoRepository.findAll()).thenReturn(List.of(pago));

        List<PagoResponseDTO> resultado = pagoService.findAllPagos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Tarjeta de crédito", resultado.get(0).getMetodoPago());
    }

    @Test
    public void testFindById() {
        long id = 1L;
        Pago pago = crearPago();
        when(pagoRepository.findById(id)).thenReturn(Optional.of(pago));

        PagoResponseDTO resultado = pagoService.findPagosById(id);

        assertNotNull(resultado);
        assertEquals("Tarjeta de crédito", resultado.getMetodoPago());
    }

    @Test
    public void testFindById_NotFound() {
        long id = 99L;
        when(pagoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            pagoService.findPagosById(id);
        });
    }

    @Test
    public void testDeletePago() {
        long id = 1L;
        Pago pago = crearPago();
        when(pagoRepository.findById(id)).thenReturn(Optional.of(pago));
        doNothing().when(pagoRepository).delete(pago);

        pagoService.deletePago(id);

        verify(pagoRepository, times(1)).delete(pago);
    }

    @Test
    public void testMakePago_MetodoInvalido() {
        PagoRequestDTO dto = new PagoRequestDTO();
        dto.setPedidoId(1L);
        dto.setMetodoPago("Cripto");

        assertThrows(IllegalArgumentException.class, () -> {
            pagoService.makePago(dto);
        });
    }

    private Pago crearPago() {
        Pago pago = new Pago();
        pago.setId(1L);
        pago.setMetodoPago("Tarjeta de crédito");
        pago.setComprobante("COMP-ABC12345");
        pago.setFecha(new Date());
        pago.setPedidoId(1L);
        return pago;
    }
}