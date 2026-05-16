package com.marketplace.usuario.exception;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j // Habilita los logs con log.error(), log.info(), etc.
@ControllerAdvice // Le dice a Spring que esta clase maneja errores de TODOS los controllers
public class GlobalExceptionHandler {

    // Este método captura cualquier RuntimeException que se lance en el sistema
    // Por ejemplo: "Usuario no encontrado", "Email ya existe", etc.
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeExceptions(RuntimeException ex) {

        // Registramos el error en consola para nosotros (aparece en rojo en IntelliJ)
        log.error("Se detectó un error en el microservicio de Usuario: {}", ex.getMessage(), ex);

        // Armamos la respuesta limpia para el cliente/Postman
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // Este método captura los errores de validación del @Valid en los controllers
    // Cuando un campo no cumple las reglas del DTO, Spring lanza esta excepción
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {

        log.error("Error de validación en los datos recibidos: {}", ex.getMessage());

        // Recorremos todos los campos que fallaron y armamos un mapa con sus mensajes
        // Por ejemplo: {"email": "El email debe tener un formato válido", "password": "Mínimo 8 caracteres"}
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            // Obtenemos el nombre del campo que falló (ej: "email", "password")
            String campo = ((FieldError) error).getField();
            // Obtenemos el mensaje definido en la anotación del DTO
            String mensaje = error.getDefaultMessage();
            errores.put(campo, mensaje);
        });

        // Devolvemos 400 con todos los campos que fallaron y sus mensajes de error
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errores);
    }
}