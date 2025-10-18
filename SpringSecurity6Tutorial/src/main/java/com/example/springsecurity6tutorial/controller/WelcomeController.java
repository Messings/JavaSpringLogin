package com.example.springsecurity6tutorial.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class WelcomeController {

    @GetMapping("/welcome")
    public String welcome(String user){
        return "welcome " + user;
    }

    @GetMapping("/welcomeAgain")
    public String welcome2(){
        return "welcome";
    }


/* Principal can only return username
    @GetMapping("")
    public Map<String, String> getUser(Principal principal) {
        return Map.of("username", principal.getName());
    }

 */

    @GetMapping("")
    public Object currentUser(Authentication authentication) {
        String username = authentication.getName();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Map.of(
                "username", username,
                "roles", roles
        );
    }

    @GetMapping("/csrf")
    public CsrfToken getToken(HttpServletRequest request){
        return (CsrfToken) request.getAttribute("_csrf");
    }

}
