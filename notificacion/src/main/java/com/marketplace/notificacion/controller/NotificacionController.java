package com.marketplace.notificacion.controller;

import com.marketplace.notificacion.DTO.NotificacionRequestDTO;
import com.marketplace.notificacion.DTO.NotificacionResponseDTO;
import com.marketplace.notificacion.model.Notificacion;
import com.marketplace.notificacion.service.NotificacionService;
import jakarta.validation.Valid;
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
    public ResponseEntity<NotificacionResponseDTO> guardar(@Valid @RequestBody NotificacionRequestDTO notificacionDTO){
        NotificacionResponseDTO nuevo = notificacionService.makeNotificacion(notificacionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }
    //READ
    //BUSCAR
    @GetMapping("/{id}")
    public ResponseEntity<NotificacionRequestDTO> buscar(@PathVariable Long id){
        try{
            NotificacionRequestDTO notificacion = notificacionService.findNotificacionesById(id);
            return ResponseEntity.ok(notificacion);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }

    }
    //LISTAR
    @GetMapping
    public ResponseEntity<List<NotificacionResponseDTO>> listar() {
        List<NotificacionResponseDTO> notificaciones = notificacionService.findAllNotificaciones();
        if (notificaciones.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(notificaciones);
    }
    //ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable long id) {
        notificacionService.deleteNotificacion(id);
        return ResponseEntity.noContent().build();
    }

}
