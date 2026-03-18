package gg.users.userapps.infraestructure.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> manejarErroresDeValidacion(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(Map.of(
                "error", "Validación fallida",
                "detalle", ex.getMessage()
        ));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> manejarErroresDeEjecucion(RuntimeException ex) {
        return ResponseEntity.status(400).body(Map.of(
                "error", "Error en la operación",
                "detalle", ex.getMessage()
        ));
    }
}