package com.ScoopLink.controller;


import com.ScoopLink.auth.dto.User;
import com.ScoopLink.auth.server.AuthServer;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/v1")
public class AuthController {

    @Resource
    private AuthServer authServer;


    @RequestMapping("/login")
    public User Login(String account, String password) {
        return authServer.Login(account, password);
    }
}
