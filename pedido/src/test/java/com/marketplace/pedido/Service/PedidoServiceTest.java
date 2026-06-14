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
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private WebClient productoWebClient;

    @Test
    public void testFindAll() {
        Pedido pedido = crearPedido();
        when(pedidoRepository.findAll()).thenReturn(List.of(pedido));

        List<PedidoResponseDTO> resultado = pedidoService.findAllPedidos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Audífonos Bluetooth", resultado.get(0).getNomProducto());
    }

    @Test
    public void testFindById() {
        long id = 1L;
        Pedido pedido = crearPedido();
        when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedido));

        PedidoResponseDTO resultado = pedidoService.findPedidoById(id);

        assertNotNull(resultado);
        assertEquals("Audífonos Bluetooth", resultado.getNomProducto());
    }

    @Test
    public void testFindById_NotFound() {
        long id = 99L;
        when(pedidoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.findPedidoById(id);
        });
    }

    @Test
    public void testDeletePedido() {
        long id = 1L;
        Pedido pedido = crearPedido();
        when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedido));
        doNothing().when(pedidoRepository).delete(pedido);

        pedidoService.deletePedido(id);

        verify(pedidoRepository, times(1)).delete(pedido);
    }

    @Test
    public void testMakePedido_PrecioInvalido() {
        PedidoRequestDTO dto = new PedidoRequestDTO();
        dto.setProductoId(1L);
        dto.setNomProducto("Audífonos");
        dto.setTipoProducto("Electrónica");
        dto.setPrecio(0);
        dto.setDireccionEntrega("Av. Libertador 1234");

        assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.makePedido(dto);
        });
    }

    private Pedido crearPedido() {
        Pedido pedido = new Pedido();
        pedido.setId(1L);
        pedido.setNomProducto("Audífonos Bluetooth");
        pedido.setTipoProducto("Electrónica");
        pedido.setPrecio(50000);
        pedido.setDireccionEntrega("Av. Libertador 1234, Santiago");
        return pedido;
    }
}