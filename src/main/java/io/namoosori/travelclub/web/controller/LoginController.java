package io.namoosori.travelclub.web.controller;

import io.namoosori.travelclub.web.service.MemberService;
import io.namoosori.travelclub.web.util.security.GoogleAuthentification;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.GeneralSecurityException;

@CrossOrigin(
        allowCredentials = "true",
        origins = {"http://localhost:3000", "http://localhost:8080", "https://localhost:3000", "https://localhost:8080"},
        methods = {RequestMethod.HEAD, RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.DELETE},
        allowedHeaders = {"Authorization", "origin", "X-AUTH-TOKEN"}
)
@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class LoginController {

    @PostMapping("/login")
    public String googleLogin(HttpServletRequest request, HttpServletResponse response) throws GeneralSecurityException, IOException {
        GoogleAuthentification googleAuthentification = new GoogleAuthentification();
        return googleAuthentification.authenticateUser(request, response);
    }

}