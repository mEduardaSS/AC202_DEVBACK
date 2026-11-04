
package com.example.ac2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<?> tratarNaoEncontrado(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<?> tratarRegraNegocio(Exception ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
            .body(Map.of("erro", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> tratarErro(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("erro", "Não foi possível processar a solicitação"));
    }
}
