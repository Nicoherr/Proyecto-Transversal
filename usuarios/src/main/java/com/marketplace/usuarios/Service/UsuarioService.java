package com.marketplace.usuarios.Service.;
import com.marketplace.usuarios.Usuario;
import com.marketplace.usuarios.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    public List<Usuario> listar() {
        return repository.findAll();
    }

    public Usuario crear(Usuario usuario) {
        if (repository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("Email ya registrado");
        }
        return repository.save(usuario);
    }

    public Usuario obtener(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Usuarios actualizar(Long id, Usuarios nuevo) {
        Usuarios u = obtener(id);

        u.setNombre(nuevo.getNombre());
        u.setEmail(nuevo.getEmail());
        u.setRol(nuevo.getRol());

        return repository.save(u);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}

