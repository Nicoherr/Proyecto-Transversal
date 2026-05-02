package com.marketplace.notificacion.service;

import com.marketplace.notificacion.DTO.NotificacionRequestDTO;
import com.marketplace.notificacion.DTO.NotificacionResponseDTO;
import com.marketplace.notificacion.model.Notificacion;
import com.marketplace.notificacion.repository.NotificacionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class NotificacionService{
    @Autowired
    private final NotificacionRepository notificacionRepository;

    private NotificacionResponseDTO makeToNotificacionResponseDTO(Notificacion notificacion){
        return new NotificacionResponseDTO(notificacion.getId(), notificacion.getAsunto(), notificacion.getMensaje(), notificacion.getFecha());
    }

    //Listar
    public List<NotificacionResponseDTO> findAllNotificaciones(){
        return notificacionRepository.findAll().stream().map(this::makeToNotificacionResponseDTO).collect(Collectors.toList());
    }

    //Buscar
    public NotificacionResponseDTO findNotificacionesById(long id) {
        Notificacion notificacion = notificacionRepository.findById(id).get();
        return makeToNotificacionResponseDTO(notificacion);
    }

    //Crear
    public NotificacionResponseDTO makeNotificacion(NotificacionRequestDTO newNotificacion) {
        Notificacion notificacion = new Notificacion(0,newNotificacion.getAsunto(), newNotificacion.getMensaje(), new Date());
        notificacion = notificacionRepository.save(notificacion);
        return makeToNotificacionResponseDTO(notificacion);

    //Eliminar
    public void deleteNotificacion(long id) {
        Notificacion notificacion = notificacionRepository.findById(id).get();
        notificacionRepository.delete(notificacion);
    }
}
