package io.namoosori.travelclub.web.controller;

import io.namoosori.travelclub.web.util.security.JWTFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@CrossOrigin(
        allowCredentials = "true",
        origins = {"http://localhost:3000/", "http://localhost:8080/"},
        methods = {RequestMethod.HEAD, RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.PUT},
        allowedHeaders = {"Authorization", "origin", "X-AUTH-TOKEN"}
)
@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class LoginController {

    //private final JWTFilter jwtFilter;

    @PostMapping("/login")
    public void authenticateUser(HttpServletRequest request, HttpServletResponse response){

        String bearerToken = request.getHeader("Authorization");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", bearerToken);

        HttpEntity<String> httpEntity = new HttpEntity <> (httpHeaders);
        List<String> credential = httpHeaders.getAccessControlExposeHeaders();

        System.out.println(bearerToken);
        credential.forEach( token -> System.out.println(token));
    }


}