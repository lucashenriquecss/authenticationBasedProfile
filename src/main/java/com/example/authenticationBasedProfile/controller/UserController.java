package com.example.authenticationBasedProfile.controller;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @GetMapping("/profiles")
    public String getParkingData() {
         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // Recuperando o nome do usuário e os papéis (roles)
        String username = authentication.getName();
        String role = authentication.getAuthorities().toString();

        // Simulando uma verificação para usuários BUSINESS
        if (role.contains("ROLE_BUSINESS")) {
            return "Acesso permitido para o usuário " + username + ": Dados do estacionamento.";
        } else {
            return "Apenas usuários BUSINESS podem acessar isso!";
        }
    }
}
