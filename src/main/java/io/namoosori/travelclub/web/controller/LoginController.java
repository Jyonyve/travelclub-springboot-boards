package io.namoosori.travelclub.web.controller;

import io.namoosori.travelclub.web.store.jpastore.repository.MemberRepository;
import io.namoosori.travelclub.web.util.security.GoogleAuthentification;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(
        allowCredentials = "true",
        origins = {"http://localhost:3000", "http://localhost:8080", "https://localhost:3000", "https://localhost:8080"},
        methods = {RequestMethod.HEAD, RequestMethod.POST, RequestMethod.GET, RequestMethod.OPTIONS, RequestMethod.PUT, RequestMethod.DELETE},
        allowedHeaders = {"origin", "Accept", "X-Requested-With", "Content-Type", "Access-Control-Request-Method", "Access-Control-Request-Headers", "Authorization", "Access-Control-Allow-Credentials", "X-AUTH-TOKEN"}
)
@RestController
@RequestMapping(value = "/login/oauth2", method =  {RequestMethod.POST, RequestMethod.GET})
@RequiredArgsConstructor
public class LoginController {

    @GetMapping("/code/google")
    public List<String> googleLogin(@RequestParam String code, @RequestParam String scope, HttpServletRequest req, HttpServletResponse res) throws IOException {
        //methods for google oauth2 login
        GoogleAuthentification googleAuthentification = GoogleAuthentification.getGoogleAuthentification();

        //get first credential
        ResponseEntity<String> response = null;
        response = googleAuthentification.firstCredential(code);
        System.out.println("response.getBody()  "+response.getBody()); //가져온 모든 유저 정보 볼수있음
        // Parsing tokens from Google API server, and set info to DB
        Map<String, String > tokensMap = googleAuthentification.responseParser(response);
        String id = googleAuthentification.insertUserInDB(tokensMap);
        List<String> userRoles = googleAuthentification.getUserRoles(id).stream()
                .map(roles -> "{\"site\" : \"" + roles + "\"}").collect(Collectors.toList());

        // Use the access token for authentication
        res.addHeader("Authorization", "Bearer " + tokensMap.get("id_token"));
        return userRoles;
    }
}