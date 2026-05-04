package com.marketplace.usuario.service;
import com.marketplace.usuario.dto.UsuarioRequestDTO;
import com.marketplace.usuario.dto.UsuarioResponseDTO;
import com.marketplace.usuario.model.Usuario;
import com.marketplace.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public UsuarioResponseDTO crear(UsuarioRequestDTO dto) {
        // 1. Verificamos si el email ya existe
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya se encuentra registrado");
        }

        // 2. Convertimos DTO a Entidad
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());
        usuario.setRol(dto.getRol());

        // 3. Guardamos
        Usuario guardado = repository.save(usuario);

        // 4. Devolvemos el ResponseDTO (sin la contraseña)
        return convertirAResponse(guardado);
    }

    public List<UsuarioResponseDTO> listar() {
        return repository.findAll().stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO obtener(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return convertirAResponse(usuario);
    }

    public UsuarioResponseDTO actualizar(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setRol(dto.getRol());
        if(dto.getPassword() != null) usuario.setPassword(dto.getPassword());

        return convertirAResponse(repository.save(usuario));
    }

    public void eliminar(Long id) {
        if(!repository.existsById(id)) throw new RuntimeException("No se puede eliminar: Usuario no existe");
        repository.deleteById(id);
    }

    // Método auxiliar para no repetir código de conversión
    private UsuarioResponseDTO convertirAResponse(Usuario u) {
        UsuarioResponseDTO res = new UsuarioResponseDTO();
        res.setId(u.getId());
        res.setNombre(u.getNombre());
        res.setEmail(u.getEmail());
        res.setRol(u.getRol());
        return res;
    }
}
