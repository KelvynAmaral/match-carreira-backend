package com.elkys.matchcarreira.infrastructure.exception;

import com.elkys.matchcarreira.api.dto.ErroResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> tratarErroValidacao(MethodArgumentNotValidException ex) {
        List<String> erros = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .toList();

        var response = new ErroResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validacao nos campos",
                Instant.now(),
                erros
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErroResponse> tratarRuntimeException(RuntimeException ex) {
        var response = new ErroResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                Instant.now(),
                null
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponse> tratarErroGenerico(Exception ex) {
        var response = new ErroResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro interno no servidor.",
                Instant.now(),
                List.of(ex.getMessage())
        );
        return ResponseEntity.internalServerError().body(response);
    }
}