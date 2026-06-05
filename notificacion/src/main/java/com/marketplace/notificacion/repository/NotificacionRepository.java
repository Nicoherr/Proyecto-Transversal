package com.marketplace.notificacion.repository;
import com.marketplace.notificacion.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository // Marca esta interfaz como un componente DAO (Data Access Object) de Spring
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    List<Notificacion> findByAsunto(String asunto);
    List<Notificacion> findByFecha(Date fecha);
    List<Notificacion> findByFechaBetween(Date start, Date end);


}
