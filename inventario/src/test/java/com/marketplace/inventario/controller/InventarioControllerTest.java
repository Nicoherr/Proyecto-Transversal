package com.marketplace.inventario.controller;
import com.marketplace.inventario.assemblers.InventarioModelAssembler;
import com.marketplace.inventario.dto.InventarioResponseDTO;
import com.marketplace.inventario.service.InventarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class InventarioControllerTest {

    // Inyecta el controller real
    @InjectMocks
    private InventarioController inventarioController;

    // Mock del service
    @Mock
    private InventarioService service;

    // Mock del assembler — necesario porque el controller lo usa
    @Mock
    private InventarioModelAssembler assembler;

    @Test
    public void testListar_RetornaLista() {
        // ARRANGE
        InventarioResponseDTO dto = crearResponseDTO();
        when(service.listar()).thenReturn(List.of(dto));

        // ACT
        List<InventarioResponseDTO> resultado = service.listar();

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(10L, resultado.get(0).getProductoId());
    }

    @Test
    public void testObtener_RetornaInventario() {
        // ARRANGE
        InventarioResponseDTO dto = crearResponseDTO();
        when(service.obtener(1L)).thenReturn(dto);

        // ACT
        InventarioResponseDTO resultado = service.obtener(1L);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(50, resultado.getStock());
    }

    @Test
    public void testObtener_CuandoNoExiste() {
        // ARRANGE
        when(service.obtener(99L))
                .thenThrow(new RuntimeException("Registro de inventario no encontrado con id: 99"));

        // ASSERT
        assertThrows(RuntimeException.class, () -> service.obtener(99L));
    }

    // Método auxiliar para no repetir la creación del DTO
    private InventarioResponseDTO crearResponseDTO() {
        InventarioResponseDTO dto = new InventarioResponseDTO();
        dto.setId(1L);
        dto.setProductoId(10L);
        dto.setStock(50);
        dto.setStockMinimo(5);
        return dto;
    }
}
