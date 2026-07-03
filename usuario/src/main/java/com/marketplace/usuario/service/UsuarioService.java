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
        log.info("[POST] Creando nuevo usuario con email: {}", dto.getEmail());
        log.debug("[POST] DTO recibido - Nombre: {}, Rol: {}", dto.getNombre(), dto.getRol());

        try {
            Usuario usuario = new Usuario();
            usuario.setNombre(dto.getNombre());
            usuario.setEmail(dto.getEmail());
            usuario.setPassword(dto.getPassword());
            usuario.setRol(dto.getRol());

            Usuario guardado = repository.save(usuario);
            log.info("[POST] Usuario creado exitosamente - ID: {}, Email: {}, Rol: {}",
                    guardado.getId(), guardado.getEmail(), guardado.getRol());

            return convertirAResponse(guardado);
        } catch (Exception e) {
            log.error("[POST] Error al crear usuario con email {}: {}", dto.getEmail(), e.getMessage());
            throw e;
        }
    }

    public List<UsuarioResponseDTO> listar() {
        log.info("[GET] Listando todos los usuarios de la base de datos");
        
        try {
            List<UsuarioResponseDTO> usuarios = repository.findAll().stream()
                    .map(this::convertirAResponse)
                    .collect(Collectors.toList());
            
            log.info("[GET] Se encontraron {} usuarios registrados", usuarios.size());
            return usuarios;
        } catch (Exception e) {
            log.error("[GET] Error al listar usuarios: {}", e.getMessage());
            throw e;
        }
    }

    public UsuarioResponseDTO obtener(Long id) {
        log.info("[GET] Buscando usuario con ID: {}", id);

        try {
            Usuario u = repository.findById(id)
                    .orElseThrow(() -> {
                        log.error("[GET] Usuario no encontrado - ID: {}", id);
                        return new RuntimeException("Usuario no encontrado con id: " + id);
                    });

            log.info("[GET] Usuario encontrado - ID: {}, Email: {}, Rol: {}, Activo: {}",
                    u.getId(), u.getEmail(), u.getRol(), u.isActivo());
            return convertirAResponse(u);
        } catch (Exception e) {
            log.error("[GET] Error al obtener usuario ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    public UsuarioResponseDTO actualizar(Long id, UsuarioRequestDTO dto) {
        log.info("[PUT] Intentando actualizar usuario ID: {} - Email: {}", id, dto.getEmail());
        log.debug("[PUT] DTO recibido - Nombre: {}, Rol: {}", dto.getNombre(), dto.getRol());

        try {
            Usuario u = repository.findById(id)
                    .orElseThrow(() -> {
                        log.error("[PUT] Usuario no encontrado - ID: {}", id);
                        return new RuntimeException("Usuario no encontrado con id: " + id);
                    });

            u.setNombre(dto.getNombre());
            u.setEmail(dto.getEmail());
            u.setPassword(dto.getPassword());
            u.setRol(dto.getRol());

            Usuario actualizado = repository.save(u);
            log.info("[PUT] Usuario actualizado exitosamente - ID: {}, Email: {}, Rol: {}",
                    actualizado.getId(), actualizado.getEmail(), actualizado.getRol());

            return convertirAResponse(actualizado);
        } catch (Exception e) {
            log.error("[PUT] Error al actualizar usuario ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    public void eliminar(Long id) {
        log.info("[DELETE] Intentando eliminar usuario ID: {}", id);

        try {
            if (!repository.existsById(id)) {
                log.error("[DELETE] Usuario no encontrado - ID: {}", id);
                throw new RuntimeException("Usuario no encontrado con id: " + id);
            }

            repository.deleteById(id);
            log.info("[DELETE] Usuario eliminado exitosamente - ID: {}", id);
        } catch (Exception e) {
            log.error("[DELETE] Error al eliminar usuario ID {}: {}", id, e.getMessage());
            throw e;
        }
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