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

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificacionService {
    private final NotificacionRepository notificacionRepository;

    private NotificacionResponseDTO makeToNotificacionResponseDTO(Notificacion notificacion) {
        return new NotificacionResponseDTO(notificacion.getId(), notificacion.getAsunto(), notificacion.getMensaje(), notificacion.getFecha());
    }

    public List<NotificacionResponseDTO> findAllNotificaciones() {
        log.info("Se listan todas las notificaciones");
        return notificacionRepository.findAll().stream()
                .map(this::makeToNotificacionResponseDTO)
                .collect(Collectors.toList());
    }

    public NotificacionResponseDTO findNotificacionesById(long id) {
        log.info("Se busca notificacion con id: {}", id);
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notificacion no encontrada con id: " + id));
        return makeToNotificacionResponseDTO(notificacion);
    }

    public NotificacionResponseDTO makeNotificacion(NotificacionRequestDTO newNotificacion) {
        log.info("Se inicia la creación de notificacion con asunto: {}", newNotificacion.getAsunto());
        Notificacion notificacion = new Notificacion();
        notificacion.setAsunto(newNotificacion.getAsunto());
        notificacion.setMensaje(newNotificacion.getMensaje());
        notificacion.setFecha(new Date());
        notificacion = notificacionRepository.save(notificacion);
        return makeToNotificacionResponseDTO(notificacion);
    }

    public void deleteNotificacion(long id) {
        log.info("Se elimina notificacion con id: {}", id);
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notificacion no encontrada con id: " + id));
        notificacionRepository.delete(notificacion);
    }

}


