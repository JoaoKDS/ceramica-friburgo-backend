package br.com.olariafriburgo.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

// Usado pelo painel de administração do site só para conferir o login.
// Como o SecurityConfig exige usuário e senha para tudo que não é leitura
// de produtos, quem chega aqui já foi autenticado: o Spring Security
// responde 401 para os demais antes de chegar neste método.
@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/verificar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void verificar() {
        // não precisa fazer nada
    }
}