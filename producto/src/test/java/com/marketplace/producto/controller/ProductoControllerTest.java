package com.marketplace.producto.controller;
import com.marketplace.producto.assemblers.ProductoModelAssembler;
import com.marketplace.producto.dto.ProductoResponseDTO;
import com.marketplace.producto.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductoControllerTest {

    // Inyecta el controller real
    @InjectMocks
    private ProductoController productoController;

    // Mock del service
    @Mock
    private ProductoService service;

    // Mock del assembler — necesario porque el controller lo usa
    @Mock
    private ProductoModelAssembler assembler;

    @Test
    public void testListar_RetornaLista() {
        // ARRANGE
        ProductoResponseDTO dto = crearResponseDTO();
        when(service.listar()).thenReturn(List.of(dto));

        // ACT
        List<ProductoResponseDTO> resultado = service.listar();

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Zapatos Nike", resultado.get(0).getNombre());
    }

    @Test
    public void testObtener_RetornaProducto() {
        // ARRANGE
        ProductoResponseDTO dto = crearResponseDTO();
        when(service.obtener(1L)).thenReturn(dto);

        // ACT
        ProductoResponseDTO resultado = service.obtener(1L);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Zapatos Nike", resultado.getNombre());
        assertTrue(resultado.isActivo());
    }

    @Test
    public void testObtener_CuandoNoExiste() {
        // ARRANGE
        when(service.obtener(99L))
                .thenThrow(new RuntimeException("Producto no encontrado con id: 99"));

        // ASSERT
        assertThrows(RuntimeException.class, () -> service.obtener(99L));
    }

    // Método auxiliar para no repetir la creación del DTO
    private ProductoResponseDTO crearResponseDTO() {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(1L);
        dto.setNombre("Zapatos Nike");
        dto.setDescripcion("Zapatos deportivos");
        dto.setPrecio(59990.0);
        dto.setStock(10);
        dto.setVendedorId(1L);
        dto.setActivo(true);
        return dto;
    }
}
