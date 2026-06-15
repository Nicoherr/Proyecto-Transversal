package com.marketplace.inventario.service;
import com.marketplace.inventario.dto.InventarioRequestDTO;
import com.marketplace.inventario.dto.InventarioResponseDTO;
import com.marketplace.inventario.model.Inventario;
import com.marketplace.inventario.repository.InventarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class InventarioServiceTest {

    // Inyecta el service real que vamos a probar
    @InjectMocks
    private InventarioService inventarioService;

    // Mock del repositorio — simula la BD sin tocarla
    @Mock
    private InventarioRepository repository;

    @Test
    public void testCrear_RetornaInventario() {
        // ARRANGE
        Inventario inventario = new Inventario();
        inventario.setId(1L);
        inventario.setProductoId(10L);
        inventario.setStock(50);
        inventario.setStockMinimo(5);

        InventarioRequestDTO dto = new InventarioRequestDTO();
        dto.setProductoId(10L);
        dto.setStock(50);
        dto.setStockMinimo(5);

        when(repository.save(any(Inventario.class))).thenReturn(inventario);

        // ACT
        InventarioResponseDTO resultado = inventarioService.crear(dto);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(10L, resultado.getProductoId());
        assertEquals(50, resultado.getStock());
        assertEquals(5, resultado.getStockMinimo());
    }

    @Test
    public void testCrear_SinStockMinimo_UsaDefault() {
        // ARRANGE — cuando stockMinimo viene en 0, el modelo usa 5 por defecto
        Inventario inventario = new Inventario();
        inventario.setId(1L);
        inventario.setProductoId(10L);
        inventario.setStock(30);
        inventario.setStockMinimo(5); // default del modelo

        InventarioRequestDTO dto = new InventarioRequestDTO();
        dto.setProductoId(10L);
        dto.setStock(30);
        dto.setStockMinimo(0); // viene en 0, no se asigna

        when(repository.save(any(Inventario.class))).thenReturn(inventario);

        // ACT
        InventarioResponseDTO resultado = inventarioService.crear(dto);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(5, resultado.getStockMinimo()); // usa el default
    }

    @Test
    public void testListar_RetornaLista() {
        // ARRANGE
        Inventario inventario = new Inventario();
        inventario.setId(1L);
        inventario.setProductoId(10L);
        inventario.setStock(50);
        inventario.setStockMinimo(5);

        when(repository.findAll()).thenReturn(List.of(inventario));

        // ACT
        List<InventarioResponseDTO> resultado = inventarioService.listar();

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(10L, resultado.get(0).getProductoId());
    }

    @Test
    public void testObtener_CuandoExiste() {
        // ARRANGE
        Inventario inventario = new Inventario();
        inventario.setId(1L);
        inventario.setProductoId(10L);
        inventario.setStock(50);
        inventario.setStockMinimo(5);

        when(repository.findById(1L)).thenReturn(Optional.of(inventario));

        // ACT
        InventarioResponseDTO resultado = inventarioService.obtener(1L);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(10L, resultado.getProductoId());
        assertEquals(50, resultado.getStock());
    }

    @Test
    public void testObtener_CuandoNoExiste() {
        // ARRANGE — simula que no existe en BD
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // ASSERT — verifica que lanza excepción
        assertThrows(RuntimeException.class, () -> inventarioService.obtener(99L));
    }
}

