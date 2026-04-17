package cl.duoc.store.ms_usuario.controller;

import cl.duoc.store.ms_usuario.model.Usuario;
import cl.duoc.store.ms_usuario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pacientes")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    //Listar
    @GetMapping
    public ResponseEntity<List<Usuario>> Listar(){
        List<Usuario> usuarios = usuarioService.findALL();
        if(usuarios.isEmpty()){
            return ResponseEntity.noContent().build();

        }
        return ResponseEntity.ok(usuarios);
    }

    //Guardar





    //Buscar



    //Eliminar

}
