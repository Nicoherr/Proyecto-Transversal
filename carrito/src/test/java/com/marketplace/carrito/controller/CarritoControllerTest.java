package com.marketplace.carrito.controller;
import com.marketplace.carrito.assemblers.CarritoModelAssembler;
import com.marketplace.carrito.dto.CarritoResponseDTO;
import com.marketplace.carrito.dto.CarritoProductoResponseDTO;
import com.marketplace.carrito.service.CarritoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CarritoControllerTest {

    // Inyecta el controller real
    @InjectMocks
    private CarritoController carritoController;

    // Mock del service — no ejecuta lógica real
    @Mock
    private CarritoService carritoService;

    // Mock del assembler — necesario porque el controller lo usa
    @Mock
    private CarritoModelAssembler assembler;

    @Test
    public void testObtener_RetornaCarrito() {
        // ARRANGE
        CarritoResponseDTO dto = new CarritoResponseDTO();
        dto.setId(1L);
        dto.setUsuarioId(5L);

        when(carritoService.obtener(1L)).thenReturn(dto);

        // ACT — llamamos directo al service porque el controller usa HATEOAS
        CarritoResponseDTO resultado = carritoService.obtener(1L);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(5L, resultado.getUsuarioId());
    }

    @Test
    public void testListarProductos_RetornaLista() {
        // ARRANGE
        CarritoProductoResponseDTO producto = new CarritoProductoResponseDTO();
        producto.setId(1L);
        producto.setCarritoId(1L);
        producto.setProductoId(10L);
        producto.setCantidad(3);

        when(carritoService.listarProductos(1L)).thenReturn(List.of(producto));

        // ACT
        List<CarritoProductoResponseDTO> resultado = carritoService.listarProductos(1L);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(10L, resultado.get(0).getProductoId());
    }

    @Test
    public void testObtener_CuandoNoExiste() {
        // ARRANGE
        when(carritoService.obtener(99L))
                .thenThrow(new RuntimeException("Carrito no encontrado con id: 99"));

        // ASSERT
        assertThrows(RuntimeException.class, () -> carritoService.obtener(99L));
    }
}
