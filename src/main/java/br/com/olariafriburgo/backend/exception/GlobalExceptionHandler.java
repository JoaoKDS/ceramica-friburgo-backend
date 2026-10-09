package br.com.olariafriburgo.backend.exception;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> tratarErroValidacao(MethodArgumentNotValidException ex) {

        var erros = ex.getBindingResult().getFieldErrors();

        var mensagens = new java.util.HashMap<String, String>();

        for (var erro : erros) {
            mensagens.put(erro.getField(), erro.getDefaultMessage());
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(mensagens);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> tratarErroGenerico(RuntimeException ex) {

        var resposta = new java.util.HashMap<String, String>();

        resposta.put("erro", ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(resposta);
    }
}