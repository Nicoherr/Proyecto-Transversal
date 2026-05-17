package com.marketplace.notificacion.DTO;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data // Anotación de Lombok: genera getters, setters, toString, equals, hashCode
public class ExceptionDTO { // DTO para estructurar respuestas de error
    private int code; // Código HTTP (ej: 404, 500, 400)
    private String type; // Tipo del error (ej: "Not Found", "Bad Request")
    private String date; // Fecha y hora cuando ocurrió el error
    private String message; // Mensaje descriptivo del error

    // Constructor #1: Recibe HttpStatus y una Exception completa

    public ExceptionDTO(HttpStatus httpStatus, Exception exception) { // 2 usages
        this.code = httpStatus.value(); // Asigna el código numérico del status HTTP (ej: 404)
        this.type = httpStatus.getReasonPhrase(); // Asigna la frase razonada (ej: "Not Found")
        this.date = (new Date()).toString(); // Asigna la fecha actual en formato String
        this.message = exception.getMessage(); // Asigna el mensaje de la excepción capturada
    }
    // Constructor #2: Recibe HttpStatus y un mensaje personalizado

    public ExceptionDTO(HttpStatus httpStatus, String message) { // 3 usages
        this.code = httpStatus.value(); // Asigna el código numérico del status HTTP
        this.type = httpStatus.getReasonPhrase(); // Asigna la frase razonada del status
        this.date = (new Date()).toString(); // Asigna la fecha actual en formato String
        this.message = message; // Asigna el mensaje personalizado recibido
    }
}
