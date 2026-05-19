package com.marketplace.notificacion.controller;
import com.marketplace.notificacion.DTO.NotificacionRequestDTO;
import com.marketplace.notificacion.DTO.NotificacionResponseDTO;
import com.marketplace.notificacion.model.Notificacion;
import com.marketplace.notificacion.service.NotificacionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Marca la clase como controlador REST (combina @Controller + @ResponseBody)
@RequestMapping("notificaciones") // Ruta base para todos los endpoints de este controlador
@RequiredArgsConstructor // Lombok: genera constructor automático para campos finales
public class NotificacionController { // Clase controladora para manejar peticiones HTTP de notificaciones

    private final NotificacionService notificacionService; // Servicio inyectado (final para @RequiredArgsConstructor)

    @GetMapping// Maneja peticiones GET a la ruta base (sin parámetros)
    public ResponseEntity<List<NotificacionResponseDTO>> getNotificaciones() { // Retorna lista de todas las notificaciones con código HTTP 200 OK
        return ResponseEntity.ok(notificacionService.findAllNotificaciones());
    }

    @GetMapping("/{id}") // Maneja peticiones GET con un ID en la URL (ej: /api/v1/notificaciones/5)
    public ResponseEntity<NotificacionResponseDTO> getNotificacion(@PathVariable long id) { // Retorna una notificación específica por ID con código HTTP 200 OK
        return ResponseEntity.ok(notificacionService.findNotificacionesById(id));
    }

    @PostMapping// Maneja peticiones POST (crear nuevo recurso)
    public ResponseEntity<NotificacionResponseDTO> postNotificacion(@Valid @RequestBody NotificacionRequestDTO notificacionDTO) { // @Valid: activa validaciones, @RequestBody: del JSON
        NotificacionResponseDTO nuevo = notificacionService.makeNotificacion(notificacionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo); // Retorna 201 CREATED con el objeto creado
    }

    @DeleteMapping("/{id}") // Maneja peticiones DELETE con un ID en la URL
    public ResponseEntity<Void> deleteNotificacion(@PathVariable long id) {
        notificacionService.deleteNotificacion(id); // Elimina la notificación (no retorna contenido)
        return ResponseEntity.noContent().build(); // Retorna 204 NO CONTENT (éxito sin cuerpo)
    }
}
