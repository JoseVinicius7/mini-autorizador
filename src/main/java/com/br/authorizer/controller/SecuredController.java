package com.br.authorizer.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/secured")
public class SecuredController {

    private static final Logger logger = LogManager.getLogger(SecuredController.class);

    @GetMapping("/hello")
    public ResponseEntity<Map<String, String>> hello(Principal principal) {
        if (principal != null) {
            logger.info("Requisição recebida de usuário autenticado: {}", principal.getName());
        } else {
            logger.warn("Requisição recebida sem usuário autenticado.");
        }

        Map<String, String> response = Map.of("message", "Bem-vindo!", "user", principal != null ? principal.getName() : "Anônimo");
        return ResponseEntity.ok(response);
    }
}
