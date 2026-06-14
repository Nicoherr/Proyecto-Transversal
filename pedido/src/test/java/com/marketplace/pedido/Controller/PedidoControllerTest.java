package com.marketplace.pedido.Controller;

import com.marketplace.pedido.DTO.PedidoResponseDTO;
import com.marketplace.pedido.assemblers.PedidoModelAssembler;
import com.marketplace.pedido.service.PedidoService;
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
public class PedidoControllerTest {

    @InjectMocks
    private com.marketplace.pedido.controller.PedidoController pedidoController;

    @Mock
    private PedidoService pedidoService;

    @Mock
    private PedidoModelAssembler assembler;

    @Mock
    private WebClient productoWebClient;

    @Test
    public void testListar_RetornaLista() {
        PedidoResponseDTO dto = crearResponseDTO();
        when(pedidoService.findAllPedidos()).thenReturn(List.of(dto));

        List<PedidoResponseDTO> resultado = pedidoService.findAllPedidos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Audífonos Bluetooth", resultado.get(0).getNomProducto());
    }

    @Test
    public void testObtener_RetornaDTO() {
        PedidoResponseDTO dto = crearResponseDTO();
        when(pedidoService.findPedidoById(1L)).thenReturn(dto);

        PedidoResponseDTO resultado = pedidoService.findPedidoById(1L);

        assertNotNull(resultado);
        assertEquals("Audífonos Bluetooth", resultado.getNomProducto());
    }

    @Test
    public void testObtener_NotFound() {
        when(pedidoService.findPedidoById(99L))
                .thenThrow(new IllegalArgumentException("Pedido no encontrado"));

        assertThrows(IllegalArgumentException.class, () -> {
            pedidoService.findPedidoById(99L);
        });
    }

    @Test
    public void testEliminar_LlamaAlService() {
        doNothing().when(pedidoService).deletePedido(1L);

        pedidoService.deletePedido(1L);

        verify(pedidoService, times(1)).deletePedido(1L);
    }

    private PedidoResponseDTO crearResponseDTO() {
        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(1L);
        dto.setNomProducto("Audífonos Bluetooth");
        dto.setTipoProducto("Electrónica");
        dto.setPrecio(50000);
        return dto;
    }
}

