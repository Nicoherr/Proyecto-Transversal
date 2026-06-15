package com.marketplace.usuario.service;
import com.marketplace.usuario.dto.UsuarioRequestDTO;
import com.marketplace.usuario.dto.UsuarioResponseDTO;
import com.marketplace.usuario.model.Usuario;
import com.marketplace.usuario.repository.UsuarioRepository;
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
public class UsuarioServiceTest {

    // Inyecta el service real que vamos a probar
    @InjectMocks
    private UsuarioService usuarioService;

    // Mock del repositorio — simula la BD sin tocarla
    @Mock
    private UsuarioRepository repository;

    @Test
    public void testCrear_RetornaUsuario() {
        // ARRANGE
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan Pérez");
        usuario.setEmail("juan@gmail.com");
        usuario.setPassword("Password1");
        usuario.setRol("USER");

        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.setNombre("Juan Pérez");
        dto.setEmail("juan@gmail.com");
        dto.setPassword("Password1");
        dto.setRol("USER");

        when(repository.save(any(Usuario.class))).thenReturn(usuario);

        // ACT
        UsuarioResponseDTO resultado = usuarioService.crear(dto);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Juan Pérez", resultado.getNombre());
        assertEquals("juan@gmail.com", resultado.getEmail());
        assertEquals("USER", resultado.getRol());
    }

    @Test
    public void testListar_RetornaLista() {
        // ARRANGE
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan Pérez");
        usuario.setEmail("juan@gmail.com");
        usuario.setRol("USER");

        when(repository.findAll()).thenReturn(List.of(usuario));

        // ACT
        List<UsuarioResponseDTO> resultado = usuarioService.listar();

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Juan Pérez", resultado.get(0).getNombre());
    }

    @Test
    public void testObtener_CuandoExiste() {
        // ARRANGE
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("María López");
        usuario.setEmail("maria@gmail.com");
        usuario.setRol("ADMIN");

        when(repository.findById(1L)).thenReturn(Optional.of(usuario));

        // ACT
        UsuarioResponseDTO resultado = usuarioService.obtener(1L);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("María López", resultado.getNombre());
        assertEquals("ADMIN", resultado.getRol());
    }

    @Test
    public void testObtener_CuandoNoExiste() {
        // ARRANGE
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // ASSERT
        assertThrows(RuntimeException.class, () -> usuarioService.obtener(99L));
    }

    @Test
    public void testActualizar_RetornaUsuarioActualizado() {
        // ARRANGE
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(1L);
        usuarioExistente.setNombre("Juan Pérez");
        usuarioExistente.setEmail("juan@gmail.com");
        usuarioExistente.setPassword("Password1");
        usuarioExistente.setRol("USER");

        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setId(1L);
        usuarioActualizado.setNombre("Juan Actualizado");
        usuarioActualizado.setEmail("juannuevo@gmail.com");
        usuarioActualizado.setPassword("Password2");
        usuarioActualizado.setRol("ADMIN");

        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.setNombre("Juan Actualizado");
        dto.setEmail("juannuevo@gmail.com");
        dto.setPassword("Password2");
        dto.setRol("ADMIN");

        when(repository.findById(1L)).thenReturn(Optional.of(usuarioExistente));
        when(repository.save(any(Usuario.class))).thenReturn(usuarioActualizado);

        // ACT
        UsuarioResponseDTO resultado = usuarioService.actualizar(1L, dto);

        // ASSERT
        assertNotNull(resultado);
        assertEquals("Juan Actualizado", resultado.getNombre());
        assertEquals("ADMIN", resultado.getRol());
    }

    @Test
    public void testActualizar_CuandoNoExiste() {
        // ARRANGE
        when(repository.findById(99L)).thenReturn(Optional.empty());

        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.setNombre("Test");
        dto.setEmail("test@gmail.com");
        dto.setPassword("Password1");
        dto.setRol("USER");

        // ASSERT
        assertThrows(RuntimeException.class, () -> usuarioService.actualizar(99L, dto));
    }

    @Test
    public void testEliminar_CuandoExiste() {
        // ARRANGE
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        // ACT
        usuarioService.eliminar(1L);

        // ASSERT — verifica que se llamó deleteById exactamente una vez
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    public void testEliminar_CuandoNoExiste() {
        // ARRANGE
        when(repository.existsById(99L)).thenReturn(false);

        // ASSERT
        assertThrows(RuntimeException.class, () -> usuarioService.eliminar(99L));
    }
}

