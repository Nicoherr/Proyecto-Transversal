package com.marketplace.pedido.Service;

import com.marketplace.pedido.DTO.PedidoRequestDTO;
import com.marketplace.pedido.DTO.PedidoResponseDTO;
import com.marketplace.pedido.model.Pedido;
import com.marketplace.pedido.repository.PedidoRepository;
import com.marketplace.pedido.service.PedidoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private WebClient productoWebClient;

    // ─── Tests CRUD ───────────────────────────────────────────────────────────

    @Test
    public void testFindAll() {
        when(pedidoRepository.findAll()).thenReturn(List.of(crearPedido()));

        List<PedidoResponseDTO> resultado = pedidoService.findAllPedidos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Laptop Gamer", resultado.get(0).getNomProducto());
    }

    @Test
    public void testFindById() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(crearPedido()));

        PedidoResponseDTO resultado = pedidoService.findPedidoById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getProductoId());
        assertEquals("Laptop Gamer", resultado.getNomProducto());
    }

    @Test
    public void testFindById_NotFound() {
        when(pedidoRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () ->
                pedidoService.findPedidoById(99L));
    }

    @Test
    public void testDeletePedido() {
        Pedido pedido = crearPedido();
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));
        doNothing().when(pedidoRepository).delete(pedido);

        pedidoService.deletePedido(1L);

        verify(pedidoRepository, times(1)).delete(pedido);
    }

    // ─── Tests reglas de negocio ──────────────────────────────────────────────

    @Test
    public void testMakePedido_PrecioCero() {
        PedidoRequestDTO dto = crearRequest();
        dto.setPrecio(0);

        assertThrows(IllegalArgumentException.class, () ->
                pedidoService.makePedido(dto));
    }

    @Test
    public void testMakePedido_PrecioNegativo() {
        PedidoRequestDTO dto = crearRequest();
        dto.setPrecio(-100);

        assertThrows(IllegalArgumentException.class, () ->
                pedidoService.makePedido(dto));
    }

    @Test
    public void testMakePedido_NombreVacio() {
        PedidoRequestDTO dto = crearRequest();
        dto.setNomProducto("   ");

        assertThrows(IllegalArgumentException.class, () ->
                pedidoService.makePedido(dto));
    }

    @Test
    public void testMakePedido_DireccionVacia() {
        PedidoRequestDTO dto = crearRequest();
        dto.setDireccionEntrega("   ");

        assertThrows(IllegalArgumentException.class, () ->
                pedidoService.makePedido(dto));
    }

    // ─── Helpers ──────────────────────────────────────────────────────────────

    private Pedido crearPedido() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setProductoId(1L);
        pedido.setNomProducto("Laptop Gamer");
        pedido.setTipoProducto("Electrónica");
        pedido.setPrecio(500000);
        pedido.setDireccionEntrega("Av. Providencia 123, Santiago");
        return pedido;
    }

    private PedidoRequestDTO crearRequest() {
        PedidoRequestDTO dto = new PedidoRequestDTO();
        dto.setProductoId(1L);
        dto.setNomProducto("Laptop Gamer");
        dto.setTipoProducto("Electrónica");
        dto.setPrecio(500000);
        dto.setDireccionEntrega("Av. Providencia 123, Santiago");
        return dto;
    }
}