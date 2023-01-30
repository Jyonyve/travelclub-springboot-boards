package io.namoosori.travelclub.web.controller;

import com.auth0.jwt.impl.JWTParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.namoosori.travelclub.web.service.MemberService;
import io.namoosori.travelclub.web.service.sdo.MemberCdo;
import io.namoosori.travelclub.web.util.security.GoogleAuthentification;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(
        allowCredentials = "true",
        origins = {"http://localhost:3000", "http://localhost:8080", "https://localhost:3000", "https://localhost:8080"},
        methods = {RequestMethod.HEAD, RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.DELETE},
        allowedHeaders = {"Authorization", "origin", "X-AUTH-TOKEN"}
)
@RestController
@RequestMapping(value = "/login/oauth2", method =  {RequestMethod.POST, RequestMethod.GET})
@RequiredArgsConstructor
public class LoginController {

    @GetMapping("/code/google")
    public List<String> googleLogin(@RequestParam String code, @RequestParam String scope, HttpServletRequest req, HttpServletResponse res) throws IOException {

        ResponseEntity<String> response = null;

        RestTemplate restTemplate = new RestTemplate();

        String credentials = "642225847404-je5i44c2t5d6jskll3sk82nqh233ejlk.apps.googleusercontent.com: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";
        String encodedCredentials = new String(Base64.encodeBase64(credentials.getBytes()));
        System.out.println(encodedCredentials);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "Basic " + encodedCredentials);

        HttpEntity<String> request = new HttpEntity<>(headers);
        String access_token_url = "https://oauth2.googleapis.com/token";
        access_token_url += "?code=" + code;
        access_token_url += "&grant_type=authorization_code";
        access_token_url += "&redirect_uri=http://localhost:3000/login/oauth2/code/google";

        //sending token url(post) and getting tokens
        response = restTemplate.exchange(access_token_url, HttpMethod.POST, request, String.class);
        System.out.println("response.getBody()  "+response.getBody());

        // Parsing tokens from Google API server, and set info to DB
        GoogleAuthentification googleAuthentification = new GoogleAuthentification();
        Map<String, String > tokensMap = googleAuthentification.responseParser(response);
        String id = googleAuthentification.insertUserInDB(tokensMap);
        List<String> userRoles = googleAuthentification.getUserRoles(id);

        // Use the access token for authentication
        res.addHeader("Authorization", "Bearer " + tokensMap.get("id_token"));
        System.out.println(userRoles + ", " + res.getHeader("Authorization"));
        return userRoles;
    }
}