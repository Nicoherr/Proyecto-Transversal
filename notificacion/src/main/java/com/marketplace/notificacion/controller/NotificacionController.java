package com.marketplace.notificacion.controller;

import com.marketplace.notificacion.model.Notificacion;
import com.marketplace.notificacion.service.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    //CREATE
    @PostMapping
    public ResponseEntity<Notificacion> guardar(@RequestBody Notificacion notificacion){
        Notificacion nuevo = notificacionService.save(notificacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }
    //READ
    //BUSCAR
    @GetMapping("/{id}")
    public ResponseEntity<Notificacion> buscar(@PathVariable Long id){
        try{
            Notificacion notificacion = notificacionService.findById(id);
            return ResponseEntity.ok(notificacion);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }

    }
    //LISTAR
    @GetMapping
    public ResponseEntity<List<Notificacion>> listar() {
        List<Notificacion> notificaciones = notificacionService.findAll();
        if (notificaciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(notificaciones);
    }
    //ELIMINAR
    @DeleteMapping
    public ResponseEntity<?> eliminarTodos() {
        notificacionService.deleteAll();
        return ResponseEntity.noContent().build();
    }
























    //UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Notificacion> actualizar(@PathVariable Integer id, @RequestBody Notificacion notificacion){
        try{
            Notificacion not = notificacionService.findById(id.longValue());
            not.setId(notificacion.getId());
            not.setAsunto(notificacion.getAsunto());
            not.setMensaje(notificacion.getMensaje());
            not.setFecha(notificacion.getFecha());
            notificacionService.save(not);
            return ResponseEntity.ok(notificacion);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

}
