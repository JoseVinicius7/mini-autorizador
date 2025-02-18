package com.br.authorizer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/secured")
public class SecuredController {

    @GetMapping("/hello")
    public Map<String, String> hello(Principal principal) {
        return Map.of("message", "Bem-vindo!", "user", principal.getName());
    }
}
