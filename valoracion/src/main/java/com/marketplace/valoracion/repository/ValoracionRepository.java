package com.marketplace.valoracion.repository;

import com.marketplace.valoracion.model.Valoracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValoracionRepository extends JpaRepository<Valoracion, Long> {

    // Verifica si ya existe una valoración para ese producto (evitar duplicados)
    boolean existsByProductoId(Long productoId);

    // Obtener todas las valoraciones de un producto específico
    List<Valoracion> findByProductoId(Long productoId);

    // Obtener valoraciones por número de estrellas
    List<Valoracion> findByNumEstrella(int numEstrella);
}