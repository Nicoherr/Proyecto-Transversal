package com.marketplace.notificacion.service;

import com.marketplace.notificacion.model.Notificacion;
import com.marketplace.notificacion.repository.NotificacionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class NotificacionService{
    @Autowired
    private NotificacionRepository notificacionRepository;

    public Notificacion save(Notificacion notificacion) { return notificacionRepository.save(notificacion);
    }
    public Notificacion findById(Long id){ return notificacionRepository.findById(id).get();
    }
    public List<Notificacion> findAll(){
        return notificacionRepository.findAll();
    }


    public void delete(long id){
        Notificacion notificacion= notificacionRepository.findById(id).get();
        notificacionRepository.delete(notificacion);
    }
}
