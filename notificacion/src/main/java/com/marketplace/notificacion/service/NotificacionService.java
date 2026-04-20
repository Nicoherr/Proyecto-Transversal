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

    //CREACION DEL CRUD
    //CREATE /save(entity) cuando el objeto no tiene id
    //UPDATE /save(entity) cuando el objeto ya tiene id
    public Notificacion save(Notificacion notificacion){
        return notificacionRepository.save(notificacion);
    }



    //READ /findById(), /findAll(), /findAllById(), /existById(), /count().
    public List<Notificacion> findAll(){
        return notificacionRepository.findAll();
    }
    public Notificacion findById(long id){
        return notificacionRepository.findById(id).get();
    }




    //DELETE /deleteById(), /delete(), /deleteAll().
    public void delete(long id){
        notificacionRepository.deleteById(id);
    }

    public void deleteAll(){
        notificacionRepository.deleteAll();
    }
}
