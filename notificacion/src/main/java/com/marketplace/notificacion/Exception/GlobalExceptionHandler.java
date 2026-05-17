package com.marketplace.notificacion.Exception;

import com.marketplace.notificacion.DTO.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;
@RestControllerAdvice // Intercepta excepciones en controladores REST y permite personalizar respuestas
public class GlobalExceptionHandler { // Clase que maneja globalmente las excepciones
    @ExceptionHandler(NoSuchElementException.class) // Maneja excepciones cuando no se encuentra un elemento (ej: findById falla)
    public ResponseEntity<ExceptionDTO> handleNotFound(NoSuchElementException ex) { // Método para respuesta de "no encontrado"
        ExceptionDTO exceptionDTO = new ExceptionDTO(HttpStatus.NOT_FOUND, ex); // Crea DTO con estado 404 y la excepción
        return new ResponseEntity<>(exceptionDTO, HttpStatus.NOT_FOUND); // Retorna respuesta HTTP 404
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class) // Maneja violaciones de restricciones SQL (ej: duplicados, FK)
    public ResponseEntity<ExceptionDTO> handleSQLIntegrityConstraintViolation(SQLIntegrityConstraintViolationException ex) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(HttpStatus.CONFLICT, ex); // Crea DTO con estado 409 (conflicto)
        return new ResponseEntity<>(exceptionDTO, HttpStatus.CONFLICT); // Retorna respuesta HTTP 409
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // Maneja errores de validación de argumentos (@Valid)
    public ResponseEntity<ExceptionDTO> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream() // Obtiene todos los errores de campo
                .map(error -> error.getField() + ": " + error.getDefaultMessage()) // Convierte cada error a "campo: mensaje"
                .findFirst() // Toma SOLO el primer error encontrado
                .orElse("Error de validación"); // Si no hay errores de campo, mensaje genérico
        ExceptionDTO exceptionDTO = new ExceptionDTO(HttpStatus.BAD_REQUEST, message); // Crea DTO con estado 400
        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST); // Retorna respuesta HTTP 400
    }

    @ExceptionHandler(IllegalArgumentException.class) // Maneja argumentos inválidos (ej: ID negativo, dato incorrecto)
    public ResponseEntity<ExceptionDTO> handleIllegalArgument(IllegalArgumentException ex) { // Nota: falta el parámetro en la firma original
        ExceptionDTO exceptionDTO = new ExceptionDTO(HttpStatus.BAD_REQUEST, ex.getMessage()); // Crea DTO con estado 400
        return new ResponseEntity<>(exceptionDTO, HttpStatus.BAD_REQUEST); // Retorna respuesta HTTP 400
    }

    @ExceptionHandler(Exception.class) // Manejador genérico para CUALQUIER excepción no capturada antes
    public ResponseEntity<ExceptionDTO> handleGenericException(Exception ex) {
        ExceptionDTO exceptionDTO = new ExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor"); // Mensaje genérico sin exponer detalles
        return new ResponseEntity<>(exceptionDTO, HttpStatus.INTERNAL_SERVER_ERROR); // Retorna respuesta HTTP 500
    }
}
