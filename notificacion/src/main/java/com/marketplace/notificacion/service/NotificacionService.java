package com.marketplace.notificacion.service;

import com.marketplace.notificacion.DTO.NotificacionRequestDTO;
import com.marketplace.notificacion.DTO.NotificacionResponseDTO;
import com.marketplace.notificacion.model.Notificacion;
import com.marketplace.notificacion.repository.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service// Marca esta clase como un servicio de Spring (lógica de negocio)
@RequiredArgsConstructor// Genera constructor automático para los campos finales (Lombok)
@Slf4j// Agrega un logger automáticamente (Lombok)
public class NotificacionService {
    private final NotificacionRepository notificacionRepository; // Repositorio para acceder a la BD (inyectado automáticamente)

    private NotificacionResponseDTO makeToNotificacionResponseDTO(Notificacion notificacion) {// Convierte entidad a DTO de respuesta
        return new NotificacionResponseDTO(// Retorna un nuevo DTO con los datos de la entidad
                notificacion.getId(), // ID de la notificación
                notificacion.getAsunto(), // Asunto
                notificacion.getMensaje(), // Mensaje
                notificacion.getFecha()); // Fecha
    }

    public List<NotificacionResponseDTO> findAllNotificaciones() { // Obtiene todas las notificaciones
        log.info("Se listan todas las notificaciones"); // Registro en bitácora (log)
        return notificacionRepository.findAll().stream() // Busca todas en BD, convierte a stream
                .map(this::makeToNotificacionResponseDTO) // Convierte cada Notification a DTO
                .collect(Collectors.toList()); // Recolecta en una lista de DTOs
    }

    public NotificacionResponseDTO findNotificacionesById(long id) { // Busca notificación por ID
        log.info("Se busca notificacion con id: {}", id); // Log con el ID buscado
        Notificacion notificacion = notificacionRepository.findById(id) // Busca en BD por ID
                .orElseThrow(() -> new IllegalArgumentException("Notificacion no encontrada con id: " + id)); // Si no existe, lanza excepción
        return makeToNotificacionResponseDTO(notificacion); // Convierte a DTO y retorna
    }

    public NotificacionResponseDTO makeNotificacion(NotificacionRequestDTO newNotificacion) { // Crea una nueva notificación
        log.info("Se inicia la creación de notificacion con asunto: {}", newNotificacion.getAsunto()); // Log del asunto a crear
        Notificacion notificacion = new Notificacion(); // Crea nueva entidad vacía
        notificacion.setAsunto(newNotificacion.getAsunto()); // Asigna el asunto desde el DTO de request
        notificacion.setMensaje(newNotificacion.getMensaje()); // Asigna el mensaje (ojo: getMessage, no getMensaje)
        notificacion.setFecha(new Date()); // Asigna la fecha actual (del sistema)
        notificacion = notificacionRepository.save(notificacion); // Guarda en BD y obtiene entidad con ID generado
        return makeToNotificacionResponseDTO(notificacion); // Convierte a DTO de respuesta y retorna
    }

    public void deleteNotificacion(long id) { // Elimina notificación por ID
        log.info("Se elimina notificacion con id: {}", id); // Log del ID a eliminar
        Notificacion notificacion = notificacionRepository.findById(id) // Busca la entidad en BD
                .orElseThrow(() -> new IllegalArgumentException("Notificacion no encontrada con id: " + id)); // Si no existe, excepción
        notificacionRepository.delete(notificacion); // Elimina la entidad de la BD
    }

}


