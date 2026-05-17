package com.marketplace.notificacion.repository;
import com.marketplace.notificacion.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Marca esta interfaz como un componente DAO (Data Access Object) de Spring
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    // Esta interfaz hereda automáticamente métodos CRUD estándar:
    // save(), findById(), findAll(), delete(), count(), etc.
    // No es necesario implementar nada porque Spring Data JPA
    // genera automáticamente la implementación en tiempo de ejecución

}
