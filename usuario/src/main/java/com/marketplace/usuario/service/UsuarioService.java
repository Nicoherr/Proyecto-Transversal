package com.marketplace.usuario.service;
import com.marketplace.usuario.dto.UsuarioRequestDTO;
import com.marketplace.usuario.dto.UsuarioResponseDTO;
import com.marketplace.usuario.model.Usuario;
import com.marketplace.usuario.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public UsuarioResponseDTO crear(UsuarioRequestDTO dto) {
        log.info("Intentando crear un nuevo usuario con email: {}", dto.getEmail());

        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());
        usuario.setRol(dto.getRol());

        Usuario guardado = repository.save(usuario);
        log.info("Usuario creado exitosamente con ID: {}", guardado.getId());

        return convertirAResponse(guardado);
    }

    public List<UsuarioResponseDTO> listar() {
        log.info("Listando todos los usuarios de la base de datos");

        return repository.findAll().stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO obtener(Long id) {
        log.info("Buscando usuario con ID: {}", id);

        Usuario u = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        log.info("Usuario encontrado correctamente con ID: {}", id);
        return convertirAResponse(u);
    }

    public UsuarioResponseDTO actualizar(Long id, UsuarioRequestDTO dto) {
        log.info("Intentando actualizar usuario con ID: {}", id);

        Usuario u = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        u.setNombre(dto.getNombre());
        u.setEmail(dto.getEmail());
        u.setPassword(dto.getPassword());
        u.setRol(dto.getRol());

        Usuario actualizado = repository.save(u);
        log.info("Usuario actualizado exitosamente con ID: {}", actualizado.getId());

        return convertirAResponse(actualizado);
    }

    public void eliminar(Long id) {
        log.info("Intentando eliminar usuario con ID: {}", id);

        if (!repository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }

        repository.deleteById(id);
        log.info("Usuario eliminado exitosamente con ID: {}", id);
    }

    private UsuarioResponseDTO convertirAResponse(Usuario u) {
        UsuarioResponseDTO res = new UsuarioResponseDTO();
        res.setId(u.getId());
        res.setNombre(u.getNombre());
        res.setEmail(u.getEmail());
        res.setRol(u.getRol());
        res.setActivo(u.isActivo());
        return res;
    }
}