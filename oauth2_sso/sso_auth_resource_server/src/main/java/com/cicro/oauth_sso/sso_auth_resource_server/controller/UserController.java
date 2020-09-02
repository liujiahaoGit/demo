package com.cicro.oauth_sso.sso_auth_resource_server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {
    @GetMapping("/user")
    public Principal getCurrentUser(Principal principal) {
        return principal;
    }
}