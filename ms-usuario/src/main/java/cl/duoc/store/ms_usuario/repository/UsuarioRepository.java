package cl.duoc.store.ms_usuario.repository;
import cl.duoc.store.ms_usuario.model.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    //Listar
    @Query("SELECT u FROM Usuario u WHERE u.apellidos= : apellido")
    List<Usuario> buscarPorApellido(@Param("apellidos")String apellido);

    //Buscar por Id
    @Query("SELECT u FROM Usuario u WHERE u.id=: id ")
    List<Usuario> buscarPorId(@Param("id") int id);

    //Guardar
    @Query("SELECT u FROM Usuario u WHERE u.nombre= : nombre")
    List<Usuario> guardarUsuario(@Param("nombre")String nombre);

    //Eliminar por id
    @Query("SELECT u FROM Usuario u WHERE u.id=: id")
    List<Usuario> eliminarUsuario(@Param("id")int id);
}
