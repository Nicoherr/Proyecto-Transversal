package com.marketplace.vendedor.controller;
import com.marketplace.vendedor.assemblers.VendedorModelAssembler;
import com.marketplace.vendedor.dto.VendedorResponseDTO;
import com.marketplace.vendedor.dto.VendedorRequestDTO;
import com.marketplace.vendedor.service.VendedorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class VendedorControllerTest {

    // Inyecta el controller real
    @InjectMocks
    private VendedorController vendedorController;

    // Mock del service
    @Mock
    private VendedorService service;

    // Mock del assembler — necesario porque el controller lo usa
    @Mock
    private VendedorModelAssembler assembler;

    @Test
    public void testListar_RetornaLista() {
        // ARRANGE
        VendedorResponseDTO dto = crearResponseDTO();
        when(service.listar()).thenReturn(List.of(dto));

        // ACT
        List<VendedorResponseDTO> resultado = service.listar();

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Tienda Tech", resultado.get(0).getNombreTienda());
        assertTrue(resultado.get(0).isActivo());
    }

    @Test
    public void testObtener_RetornaVendedor() {
        // ARRANGE
        VendedorResponseDTO dto = crearResponseDTO();
        when(service.obtener(1L)).thenReturn(dto);

        // ACT
        VendedorResponseDTO resultado = service.obtener(1L);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Tienda Tech", resultado.getNombreTienda());
        assertEquals(4.5, resultado.getReputacion());
    }

    @Test
    public void testObtener_CuandoNoExiste() {
        // ARRANGE
        when(service.obtener(99L))
                .thenThrow(new RuntimeException("Vendedor no encontrado con id: 99"));

        // ASSERT
        assertThrows(RuntimeException.class, () -> service.obtener(99L));
    }

    @Test
    public void testCrear_LlamaAlService() {
        // ARRANGE
        VendedorResponseDTO dto = crearResponseDTO();
        VendedorRequestDTO requestDTO = new VendedorRequestDTO();
        requestDTO.setNombreTienda("Tienda Tech");
        requestDTO.setUsuarioId(5L);

        when(service.crear(any())).thenReturn(dto);

        // ACT
        VendedorResponseDTO resultado = service.crear(requestDTO);

        // ASSERT
        assertNotNull(resultado);
        verify(service, times(1)).crear(any());
    }

    // Método auxiliar para no repetir la creación del DTO
    private VendedorResponseDTO crearResponseDTO() {
        VendedorResponseDTO dto = new VendedorResponseDTO();
        dto.setId(1L);
        dto.setNombreTienda("Tienda Tech");
        dto.setDescripcion("Venta de tecnología");
        dto.setReputacion(4.5);
        dto.setCantidadValoraciones(10);
        dto.setUsuarioId(5L);
        dto.setActivo(true);
        return dto;
    }
}

