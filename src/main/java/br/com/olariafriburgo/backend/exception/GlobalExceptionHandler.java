package br.com.olariafriburgo.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Campo vazio ou grande demais: responde 400 dizendo quais campos têm problema.
    // Os outros erros (produto não encontrado, falha do servidor...) ficam por
    // conta do próprio Spring, que já responde com o código certo.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> tratarErroValidacao(MethodArgumentNotValidException ex) {

        Map<String, String> mensagens = new HashMap<>();

        for (var erro : ex.getBindingResult().getFieldErrors()) {
            mensagens.put(erro.getField(), erro.getDefaultMessage());
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(mensagens);
    }
}