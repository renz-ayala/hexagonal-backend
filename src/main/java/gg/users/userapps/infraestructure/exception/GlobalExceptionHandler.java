package gg.users.userapps.infraestructure.exception;

import gg.users.userapps.infraestructure.exception.object.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(IllegalArgumentException ex) {
        return this.buildResponse("Validación fallida", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handleRuntimeException(RuntimeException ex) {
        return this.buildResponse("Error en la operación", ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ExceptionResponse> buildResponse(String error, String detalle, HttpStatus status) {
        return ResponseEntity.status(status).body(
                new ExceptionResponse(error, detalle)
        );
    }
}