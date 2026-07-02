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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PagoServiceTest {

    @InjectMocks
    private PagoService pagoService;

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private WebClient pedidoWebClient;

    // ─── Tests CRUD ───────────────────────────────────────────────────────────

    @Test
    public void testFindAll() {
        when(pagoRepository.findAll()).thenReturn(List.of(crearPago()));

        List<PagoResponseDTO> resultado = pagoService.findAllPagos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Tarjeta de crédito", resultado.get(0).getMetodoPago());
    }

    @Test
    public void testFindById() {
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(crearPago()));

        PagoResponseDTO resultado = pagoService.findPagosById(1L);

        assertNotNull(resultado);
        assertEquals("Tarjeta de crédito", resultado.getMetodoPago());
        assertEquals(1L, resultado.getPedidoId());
    }

    @Test
    public void testFindById_NotFound() {
        when(pagoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () ->
                pagoService.findPagosById(99L));
    }

    @Test
    public void testDeletePago() {
        Pago pago = crearPago();
        when(pagoRepository.findById(1L)).thenReturn(Optional.of(pago));
        doNothing().when(pagoRepository).delete(pago);

        pagoService.deletePago(1L);

        verify(pagoRepository, times(1)).delete(pago);
    }

    // ─── Tests reglas de negocio ──────────────────────────────────────────────

    @Test
    public void testMakePago_MetodoInvalido() {
        PagoRequestDTO dto = crearRequest();
        dto.setMetodoPago("Cripto");

        assertThrows(IllegalArgumentException.class, () ->
                pagoService.makePago(dto));
    }

    @Test
    public void testMakePago_MetodoVacio() {
        PagoRequestDTO dto = crearRequest();
        dto.setMetodoPago("");

        assertThrows(IllegalArgumentException.class, () ->
                pagoService.makePago(dto));
    }

    @Test
    public void testMakePago_Duplicado() {
        PagoRequestDTO dto = crearRequest();
        when(pagoRepository.existsByPedidoId(dto.getPedidoId())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
                pagoService.makePago(dto));
    }

    // ─── Helpers ──────────────────────────────────────────────────────────────

    private Pago crearPago() {
        Pago pago = new Pago();
        pago.setId(1L);
        pago.setPedidoId(1L);
        pago.setMetodoPago("Tarjeta de crédito");
        pago.setComprobante("COMP-ABC12345");
        pago.setFecha(new Date());
        return pago;
    }

    private PagoRequestDTO crearRequest() {
        PagoRequestDTO dto = new PagoRequestDTO();
        dto.setPedidoId(1L);
        dto.setMetodoPago("Tarjeta de crédito");
        return dto;
    }
}
