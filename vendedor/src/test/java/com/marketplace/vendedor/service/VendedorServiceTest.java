package com.marketplace.vendedor.service;
import com.marketplace.vendedor.dto.VendedorRequestDTO;
import com.marketplace.vendedor.dto.VendedorResponseDTO;
import com.marketplace.vendedor.model.Vendedor;
import com.marketplace.vendedor.repository.VendedorRepository;
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
public class VendedorServiceTest {

    // Inyecta el service real que vamos a probar
    @InjectMocks
    private VendedorService vendedorService;

    // Mock del repositorio — simula la BD sin tocarla
    @Mock
    private VendedorRepository repository;

    @Test
    public void testCrear_RetornaVendedor() {
        // ARRANGE
        Vendedor vendedor = new Vendedor();
        vendedor.setId(1L);
        vendedor.setNombreTienda("Tienda Tech");
        vendedor.setDescripcion("Venta de tecnología");
        vendedor.setUsuarioId(5L);
        vendedor.setReputacion(0.0);
        vendedor.setCantidadValoraciones(0);
        vendedor.setActivo(true);

        VendedorRequestDTO dto = new VendedorRequestDTO();
        dto.setNombreTienda("Tienda Tech");
        dto.setDescripcion("Venta de tecnología");
        dto.setUsuarioId(5L);

        when(repository.save(any(Vendedor.class))).thenReturn(vendedor);

        // ACT
        VendedorResponseDTO resultado = vendedorService.crear(dto);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Tienda Tech", resultado.getNombreTienda());
        assertEquals(5L, resultado.getUsuarioId());
        assertEquals(0.0, resultado.getReputacion());
        assertEquals(0, resultado.getCantidadValoraciones());
        assertTrue(resultado.isActivo());
    }

    @Test
    public void testCrear_ReputacionYValoracionesEnCero() {
        // ARRANGE — verifica que reputacion y cantidadValoraciones empiezan en 0
        Vendedor vendedor = new Vendedor();
        vendedor.setId(1L);
        vendedor.setNombreTienda("Nueva Tienda");
        vendedor.setUsuarioId(1L);
        vendedor.setReputacion(0.0);
        vendedor.setCantidadValoraciones(0);
        vendedor.setActivo(true);

        VendedorRequestDTO dto = new VendedorRequestDTO();
        dto.setNombreTienda("Nueva Tienda");
        dto.setUsuarioId(1L);

        when(repository.save(any(Vendedor.class))).thenReturn(vendedor);

        // ACT
        VendedorResponseDTO resultado = vendedorService.crear(dto);

        // ASSERT
        assertEquals(0.0, resultado.getReputacion());
        assertEquals(0, resultado.getCantidadValoraciones());
    }

    @Test
    public void testListar_RetornaLista() {
        // ARRANGE
        Vendedor vendedor = new Vendedor();
        vendedor.setId(1L);
        vendedor.setNombreTienda("Tienda Tech");
        vendedor.setUsuarioId(5L);
        vendedor.setReputacion(4.5);
        vendedor.setCantidadValoraciones(10);
        vendedor.setActivo(true);

        when(repository.findAll()).thenReturn(List.of(vendedor));

        // ACT
        List<VendedorResponseDTO> resultado = vendedorService.listar();

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Tienda Tech", resultado.get(0).getNombreTienda());
        assertEquals(4.5, resultado.get(0).getReputacion());
    }

    @Test
    public void testObtener_CuandoExiste() {
        // ARRANGE
        Vendedor vendedor = new Vendedor();
        vendedor.setId(1L);
        vendedor.setNombreTienda("Tienda Tech");
        vendedor.setDescripcion("Venta de tecnología");
        vendedor.setUsuarioId(5L);
        vendedor.setReputacion(4.5);
        vendedor.setCantidadValoraciones(10);
        vendedor.setActivo(true);

        when(repository.findById(1L)).thenReturn(Optional.of(vendedor));

        // ACT
        VendedorResponseDTO resultado = vendedorService.obtener(1L);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Tienda Tech", resultado.getNombreTienda());
        assertTrue(resultado.isActivo());
    }

    @Test
    public void testObtener_CuandoNoExiste() {
        // ARRANGE — simula que no existe en BD
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // ASSERT — verifica que lanza excepción
        assertThrows(RuntimeException.class, () -> vendedorService.obtener(99L));
    }
}

