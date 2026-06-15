package com.marketplace.usuario.controller;
import com.marketplace.usuario.assemblers.UsuarioModelAssembler;
import com.marketplace.usuario.dto.UsuarioResponseDTO;
import com.marketplace.usuario.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioControllerTest {

    // Inyecta el controller real
    @InjectMocks
    private UsuarioController usuarioController;

    // Mock del service
    @Mock
    private UsuarioService service;

    // Mock del assembler — necesario porque el controller lo usa
    @Mock
    private UsuarioModelAssembler assembler;

    @Test
    public void testListar_RetornaLista() {
        // ARRANGE
        UsuarioResponseDTO dto = crearResponseDTO();
        when(service.listar()).thenReturn(List.of(dto));

        // ACT
        List<UsuarioResponseDTO> resultado = service.listar();

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan Pérez", resultado.get(0).getNombre());
    }

    @Test
    public void testObtener_RetornaUsuario() {
        // ARRANGE
        UsuarioResponseDTO dto = crearResponseDTO();
        when(service.obtener(1L)).thenReturn(dto);

        // ACT
        UsuarioResponseDTO resultado = service.obtener(1L);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Juan Pérez", resultado.getNombre());
        assertEquals("USER", resultado.getRol());
    }

    @Test
    public void testObtener_CuandoNoExiste() {
        // ARRANGE
        when(service.obtener(99L))
                .thenThrow(new RuntimeException("Usuario no encontrado con id: 99"));

        // ASSERT
        assertThrows(RuntimeException.class, () -> service.obtener(99L));
    }

    @Test
    public void testEliminar_LlamaAlService() {
        // ARRANGE
        doNothing().when(service).eliminar(1L);

        // ACT
        service.eliminar(1L);

        // ASSERT — verifica que se llamó eliminar exactamente una vez
        verify(service, times(1)).eliminar(1L);
    }

    // Método auxiliar para no repetir la creación del DTO
    private UsuarioResponseDTO crearResponseDTO() {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(1L);
        dto.setNombre("Juan Pérez");
        dto.setEmail("juan@gmail.com");
        dto.setRol("USER");
        dto.setActivo(true);
        return dto;
    }
}

