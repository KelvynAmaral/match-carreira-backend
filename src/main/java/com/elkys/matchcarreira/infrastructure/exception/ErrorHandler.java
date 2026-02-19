package com.elkys.matchcarreira.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity handleRegraNegocio(RegraNegocioException ex) {
        return ResponseEntity.badRequest().body(new DadosErro(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationError(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors().stream()
                .map(DadosErroValidacao::new).toList();
        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity handleNotFoundError() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity handleAccessDeniedError() {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new DadosErro("Acesso negado."));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleBadRequestError(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(new DadosErro("Erro na leitura do JSON: verifique o formato dos dados (ex: datas)."));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity handleInternalServerError(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new DadosErro("Erro interno: " + ex.getLocalizedMessage()));
    }

    private record DadosErro(String mensagem) {}
    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao(FieldError erro) {
            this(erro.getField(), erro.getDefaultMessage());
        }
    }
}