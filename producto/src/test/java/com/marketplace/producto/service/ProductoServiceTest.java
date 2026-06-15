package com.marketplace.producto.service;
import com.marketplace.producto.dto.ProductoRequestDTO;
import com.marketplace.producto.dto.ProductoResponseDTO;
import com.marketplace.producto.model.Producto;
import com.marketplace.producto.repository.ProductoRepository;
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
public class ProductoServiceTest {

    // Inyecta el service real que vamos a probar
    @InjectMocks
    private ProductoService productoService;

    // Mock del repositorio — simula la BD sin tocarla
    @Mock
    private ProductoRepository repository;

    @Test
    public void testCrear_RetornaProducto() {
        // ARRANGE
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Zapatos Nike");
        producto.setDescripcion("Zapatos deportivos");
        producto.setPrecio(59990.0);
        producto.setStock(10);
        producto.setVendedorId(1L);
        producto.setActivo(true);

        ProductoRequestDTO dto = new ProductoRequestDTO();
        dto.setNombre("Zapatos Nike");
        dto.setDescripcion("Zapatos deportivos");
        dto.setPrecio(59990.0);
        dto.setStock(10);
        dto.setVendedorId(1L);

        when(repository.save(any(Producto.class))).thenReturn(producto);

        // ACT
        ProductoResponseDTO resultado = productoService.crear(dto);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Zapatos Nike", resultado.getNombre());
        assertEquals(59990.0, resultado.getPrecio());
        assertEquals(10, resultado.getStock());
        assertTrue(resultado.isActivo());
    }

    @Test
    public void testListar_RetornaLista() {
        // ARRANGE
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Zapatos Nike");
        producto.setPrecio(59990.0);
        producto.setStock(10);
        producto.setVendedorId(1L);
        producto.setActivo(true);

        when(repository.findAll()).thenReturn(List.of(producto));

        // ACT
        List<ProductoResponseDTO> resultado = productoService.listar();

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Zapatos Nike", resultado.get(0).getNombre());
    }

    @Test
    public void testObtener_CuandoExiste() {
        // ARRANGE
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Zapatos Nike");
        producto.setPrecio(59990.0);
        producto.setStock(10);
        producto.setVendedorId(1L);
        producto.setActivo(true);

        when(repository.findById(1L)).thenReturn(Optional.of(producto));

        // ACT
        ProductoResponseDTO resultado = productoService.obtener(1L);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Zapatos Nike", resultado.getNombre());
        assertTrue(resultado.isActivo());
    }

    @Test
    public void testObtener_CuandoNoExiste() {
        // ARRANGE — simula que no existe en BD
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // ASSERT — verifica que lanza excepción
        assertThrows(RuntimeException.class, () -> productoService.obtener(99L));
    }

    @Test
    public void testCrear_ProductoActivoPorDefecto() {
        // ARRANGE — verifica que activo siempre viene en true
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Polera Adidas");
        producto.setPrecio(19990.0);
        producto.setStock(5);
        producto.setVendedorId(2L);
        producto.setActivo(true); // siempre true por defecto en el modelo

        ProductoRequestDTO dto = new ProductoRequestDTO();
        dto.setNombre("Polera Adidas");
        dto.setPrecio(19990.0);
        dto.setStock(5);
        dto.setVendedorId(2L);

        when(repository.save(any(Producto.class))).thenReturn(producto);

        // ACT
        ProductoResponseDTO resultado = productoService.crear(dto);

        // ASSERT
        assertTrue(resultado.isActivo());
    }
}
