package cl.duoc.store.ms_usuario.service;


import cl.duoc.store.ms_usuario.model.Usuario;
import cl.duoc.store.ms_usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    //Listar
    public List<Usuario> findALL(){
        return usuarioRepository.findAll();
    }
    //Buscar por id
    public Usuario findById(long id){
        return usuarioRepository.findById(id).get();
    }
    //Guardar
    public Usuario save(Usuario usuario){
        return usuarioRepository.save(usuario);
    }
    //eliminar
    public void delete(long id){
        usuarioRepository.deleteById(id);
    }
}
