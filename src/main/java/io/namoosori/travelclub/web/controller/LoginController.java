package io.namoosori.travelclub.web.controller;

import io.namoosori.travelclub.web.util.security.JWTFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class LoginController {

    private final JWTFilter jwtFilter;

    @PostMapping("/login")
    public void authenticateUser(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) ->{
            System.out.println("Header Name: "+key+" Header Value: "+value);
        });
    }


}